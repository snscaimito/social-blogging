import express from 'express'
import morgan from 'morgan'
import { setupDatabase, addComment, findComments } from './database.js'

const app = express()
const port = process.env.PORT || 3000

app.use(morgan('dev'))
app.use(express.urlencoded({ extended: true }))
app.set('view engine', 'ejs')
app.set('views', 'src/views')

app.post('/comment', (req, res) => {
  // TODO who posted this comment?
  const comment = req.body.comment
  const referer = req.headers.referer // TODO this referer is wrong

  return addComment(comment, referer)
    .then(() => {
      res.redirect('/')
    })
    .catch((err) => {
      console.error(err.message)
    })
})

app.get('/', (req, res) => {
  const referer = req.headers.referer
  findComments(referer)
    .then((comments) => {
      res.render('index', { comments })
    })
    .catch((err) => {
      console.error(err.message)
      res.render('index', { comments: [] })
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
