import { open } from 'sqlite'
import sqlite3 from 'sqlite3'

let database

function setupDatabase () {
  return open({
    filename: ':memory:',
    driver: sqlite3.Database
  })
    .then((db) => {
      database = db
    })
    .then(() => {
      return database.run(`CREATE TABLE IF NOT EXISTS articles (
        url TEXT PRIMARY KEY)`)
        .then(() => {
          console.log('Created the articles table.')
        })
        .then(() => {
          return database.run(`CREATE TABLE IF NOT EXISTS comments (
          id INTEGER PRIMARY KEY,
          comment TEXT,
          article_url TEXT,
          FOREIGN KEY(article_url) REFERENCES articles(url)
          )`)
            .then(() => {
              console.log('Created the comments table.')
            })
        })
    })
    .catch((err) => {
      console.error(err.message)
    })
}

function closeDatabase () {
  return database.close()
    .then(() => {
      console.log('Closed the database connection.')
    })
}

// Listen for SIGINT signal (Ctrl+C)
process.on('SIGINT', () => {
  closeDatabase()
    .then(() => {
      process.exit(0)
    })
})

// Listen for SIGTERM signal (kill)
process.on('SIGTERM', () => {
  closeDatabase()
    .then(() => {
      process.exit(0)
    })
})

function addComment (comment, articleUrl) {
  console.log('database addComment', comment, articleUrl)
  const sql = 'INSERT INTO comments (comment, article_url) VALUES (?, ?)'
  return database.run(sql, [comment, articleUrl])
}

function findComments (articleUrl) {
  const sql = 'SELECT * FROM comments WHERE article_url = ?'
  return database.all(sql, [articleUrl])
}

export {
  setupDatabase,
  addComment,
  findComments
}
