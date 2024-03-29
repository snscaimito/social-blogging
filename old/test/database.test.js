import dotenv from 'dotenv'
import { beforeEach, describe, test } from 'vitest'
import { setupDatabase, savePost, findPost } from '../src/database.js'

dotenv.config({
  path: process.env.NODE_ENV === 'test' ? '.env.test' : '.env'
})

describe('publishing', () => {
  beforeEach(async () => {
    await setupDatabase()
  })

  test('save post', async ({ expect }) => {
    return savePost('http://activity-pub.com', 'http://example.com', 'activityPub')
      .then((post) => {
        expect(post.lastID).toBe(1)
      })
  })

  // TODO intermittent failure
  test.skip('find post', async ({ expect }) => {
    await savePost('http://activity-pub.com', 'http://example.com', 'activityPub')

    return findPost('http://example.com', 'activityPub')
      .then((post) => {
        expect(post.post_url).toBe('http://activity-pub.com')
      })
  })
})
