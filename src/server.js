import express from 'express'
import morgan from 'morgan'

const app = express()
const port = 3000

app.use(morgan('dev'))
app.set('view engine', 'ejs')
app.set('views', 'src/views')

app.get('/', (req, res) => {
  res.render('index', {
    title: 'Hello, World!',
    referer: req.headers.referer
  })

  // use referer to find the comments for this post
  const referer = req.headers.referer
  console.log('Referer:', referer)
})

app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`)
})

export default app
