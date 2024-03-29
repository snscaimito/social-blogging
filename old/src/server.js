import dotenv from 'dotenv'
import express from 'express'
import { setupDatabase, findPost } from './database.js'
import { publishFromRss } from './rss.js'

if (!process.env.NODE_ENV) {
  console.log('Setting NODE_ENV to "development" as it was not set.')
  process.env.NODE_ENV = 'development'
}

dotenv.config({
  path: process.env.NODE_ENV === 'test' ? '.env.test' : '.env'
})

const app = express()
const port = process.env.PORT || 3000

app.get('/posted-on/:service', (req, res) => {
  const service = req.params.service
  const articleUrl = req.query.articleUrl

  return findPost(articleUrl, service)
    .then((post) => {
      if (!post) {
        res.status(404).json({ error: 'Post not found' })
        return
      }

      res.json({ postUrl: post.post_url })
    })
    .catch((err) => {
      res.status(500).json({ error: err.message })
    })
})

function startPublishing () {
  const publishingInterval = process.env.POSTING_INTERVAL || 60000

  publishFromRss()
  setInterval(() => {
    publishFromRss()
  }, publishingInterval)
}

function startServer () {
  return setupDatabase()
    .then(() => {
      app.listen(port, () => {
        console.log(`Server listening at http://localhost:${port}`)

        startPublishing()

        process.on('SIGINT', () => {
          console.log('Shutting down server...')
          process.exit(0)
        })
      })
    })
    .catch((err) => {
      console.error(err.message)
    })
}

startServer()

export default app
