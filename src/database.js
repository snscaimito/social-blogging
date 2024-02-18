import dotenv from 'dotenv'
import { open } from 'sqlite'
import sqlite3 from 'sqlite3'

dotenv.config()

let database

function setupDatabase () {
  return open({
    filename: process.env.SQLITE_FILENAME,
    driver: sqlite3.Database
  })
    .then((db) => {
      database = db
    })
    .then(() => {
      return database.run(`CREATE TABLE IF NOT EXISTS articles (
        id INTEGER PRIMARY KEY,
        url TEXT UNIQUE
        )`)
        .then(() => {
          console.log('Created the articles table.')
        })
        .then(() => {
          return database.run(`CREATE TABLE IF NOT EXISTS networks (
          id INTEGER PRIMARY KEY,
          name TEXT UNIQUE
          )`)
            .then(() => {
              console.log('Created the networks table.')
            })
        })
        .then(() => {
          return database.run(`CREATE TABLE IF NOT EXISTS posts (
          id INTEGER PRIMARY KEY,
          article_id INTEGER,
          network_id INTEGER,
          posted_on DATETIME DEFAULT CURRENT_TIMESTAMP,
          post_url TEXT,
          FOREIGN KEY(article_id) REFERENCES articles(id),
          FOREIGN KEY(network_id) REFERENCES networks(id)
          )`)
            .then(() => {
              console.log('Created the posts table.')
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

function findNetwork (name) {
  const sql = 'SELECT * FROM networks WHERE name = ?'
  return database.get(sql, [name])
}

function insertNetwork (name) {
  const sql = 'INSERT INTO networks (name) VALUES (?)'
  return database.run(sql, [name])
    .then((result) => {
      return findNetwork(name)
    })
}

function findOrCreateNetwork (name) {
  return findNetwork(name)
    .then((network) => {
      if (network) {
        return Promise.resolve(network)
      } else {
        return insertNetwork(name)
      }
    })
}

function findArticle (url) {
  const sql = 'SELECT * FROM articles WHERE url = ?'
  return database.get(sql, [url])
}

function insertArticle (url) {
  const sql = 'INSERT INTO articles (url) VALUES (?)'
  return database.run(sql, [url])
    .then((result) => {
      return findArticle(url)
    })
}

function findOrCreateArticle (url) {
  return findArticle(url)
    .then((article) => {
      if (article) {
        return Promise.resolve(article)
      } else {
        return insertArticle(url)
      }
    })
}

function savePost (postUrl, articleUrl, networkName) {
  return findOrCreateArticle(articleUrl)
    .then((article) => {
      return findOrCreateNetwork(networkName)
        .then((network) => {
          const sql = 'INSERT INTO posts (post_url, article_id, network_id) VALUES (?, ?, ?)'
          return database.run(sql, [postUrl, article.id, network.id])
        })
    })
    .catch((err) => {
      console.error(err.message)
    })
}

function findPost (articleUrl, networkName) {
  const sql = `SELECT * FROM posts
  JOIN articles ON posts.article_id = articles.id
  JOIN networks ON posts.network_id = networks.id
  WHERE articles.url = ? AND networks.name = ?`
  return database.get(sql, [articleUrl, networkName])
}

export {
  setupDatabase,
  savePost,
  findPost
}
