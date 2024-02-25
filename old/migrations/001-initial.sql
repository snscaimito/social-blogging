--------------------------------------------------------------------------------
-- Up
--------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS articles (
    id INTEGER PRIMARY KEY,
    url TEXT UNIQUE
);

CREATE INDEX IF NOT EXISTS articles_url_index ON articles(url);

CREATE TABLE IF NOT EXISTS networks (
      id INTEGER PRIMARY KEY,
      name TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS posts (
      id INTEGER PRIMARY KEY,
      article_id INTEGER,
      network_id INTEGER,
      posted_on DATETIME DEFAULT CURRENT_TIMESTAMP,
      post_url TEXT,
      FOREIGN KEY(article_id) REFERENCES articles(id),
      FOREIGN KEY(network_id) REFERENCES networks(id)
);

--------------------------------------------------------------------------------
-- Down
--------------------------------------------------------------------------------

DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS networks;
DROP INDEX IF EXISTS articles_url_index;
DROP TABLE IF EXISTS articles;
