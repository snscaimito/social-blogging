import { open } from 'sqlite'
import sqlite3 from 'sqlite3'

let database

async function setupDatabase () {
  const SQLITE_FILENAME = process.env.SQLITE_FILENAME || '/var/lib/sqlite/published.db'

  database = await open({
    filename: SQLITE_FILENAME,
    driver: sqlite3.Database
  })

  await database.migrate({
    migrationsPath: './migrations'
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

async function findNetwork (name) {
  const sql = 'SELECT * FROM networks WHERE name = ?'
  return database.get(sql, [name])
}

async function insertNetwork (name) {
  const sql = 'INSERT INTO networks (name) VALUES (?)'
  return database.run(sql, [name])
    .then((result) => {
      return findNetwork(name)
    })
}

async function findOrCreateNetwork (name) {
  const network = await findNetwork(name)

  if (network) {
    return Promise.resolve(network)
  } else {
    return insertNetwork(name)
  }
}

async function findArticle (url) {
  const sql = 'SELECT * FROM articles WHERE url = ?'
  return database.get(sql, [url])
}

async function insertArticle (url) {
  const sql = 'INSERT INTO articles (url) VALUES (?)'
  return database.run(sql, [url])
}

async function findOrCreateArticle (url) {
  const article = await findArticle(url)
  console.log('findOrCreateArticle', article)

  if (article) {
    return Promise.resolve(article)
  } else {
    const savedArticle = await insertArticle(url)
    console.log('savedArticle', savedArticle)
    return savedArticle
  }
}

async function savePost (postUrl, articleUrl, networkName) {
  const article = await findOrCreateArticle(articleUrl)
  const network = await findOrCreateNetwork(networkName)

  console.log('Saving post', postUrl, 'for article', article.id, 'on network', network.id)

  const sql = 'INSERT INTO posts (post_url, article_id, network_id) VALUES (?, ?, ?)'
  return database.run(sql, [postUrl, article.id, network.id])
}

async function findPost (articleUrl, networkName) {
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
