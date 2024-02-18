import express from 'express'
import { setupDatabase, addComment, findComments, deleteComment } from './database.js'

const app = express()
const port = process.env.PORT || 3000

app.get('/posted-on/:service', (req, res) => {
  const service = req.params.service
  console.log(`Posted on ${service}`)
  res.json({ postUrl: 'http://example.com' })
})

app.get('/comments', (req, res) => {
  const referer = req.headers.referer
  findComments(referer)
    .then((comments) => {
      res.json(comments)
    })
    .catch((err) => {
      console.error(err.message)
      res.json([])
    })
})

app.post('/comments', (req, res) => {
  // TODO who posted this comment?
  const comment = req.body.comment
  const referer = req.headers.referer // TODO this referer is wrong

  return addComment(comment, referer)
    .then(() => {
      res.sendStatus(200)
    })
    .catch((err) => {
      console.error(err.message)
    })
})

app.delete('/comments/:id', (req, res) => {
  // TODO who is deleting this comment?
  const id = req.params.id
  deleteComment(id)
    .then(() => {
      res.sendStatus(204)
    })
    .catch((err) => {
      console.error(err.message)
    })
})

function startServer () {
  return setupDatabase()
    .then(() => {
      console.log('Database setup complete.')
    })
    .then(() => {
      app.listen(port, () => {
        console.log(`Server listening at http://localhost:${port}`)
      })
    })
    .catch((err) => {
      console.error(err.message)
    })
}

startServer()

export default app
