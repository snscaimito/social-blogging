import './setupTests.js'
import { beforeEach, describe, test } from 'vitest'
import { setupDatabase, findPost } from '../src/database.js'
import { postToActivityPub } from '../src/activityPub.js'

describe('publishing', () => {
  beforeEach(async () => {
    await setupDatabase()
  })

  test('post article', async ({ expect }) => {
    return postToActivityPub('https://example.com/article')
  })

  test('find article details', async ({ expect }) => {
    return postToActivityPub('https://example.com/article')
      .then(() => {
        return findPost('https://example.com/article', 'activityPub')
          .then((post) => {
            expect(post.post_url).toBe('http://activity-pub.com')
          })
      })
  })
})
