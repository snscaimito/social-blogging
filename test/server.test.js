import './setupTests.js'
import { describe, test, beforeEach } from 'vitest'
import request from 'supertest'
import app from '../src/server.js'
import { savePost, setupDatabase } from '../src/database.js'

describe('server', () => {
  beforeEach(async () => {
    await setupDatabase()
  })

  test('GET /posted-on/activityPub', ({ expect }) => {
    return savePost('http://activity-pub.com', 'http://example.com', 'activityPub')
      .then((postSaved) => {
        return request(app)
          .get('/posted-on/activityPub?articleUrl=http://example.com')
          .then((response) => {
            expect(response.status).toBe(200)
            expect(response.body.postUrl).toBe('http://activity-pub.com')
          })
      })
  })

  test('Not found: GET /posted-on/twitter', ({ expect }) => {
    return savePost('http://activity-pub.com', 'http://example.com', 'activityPub')
      .then((postSaved) => {
        return request(app)
          .get('/posted-on/twitter?articleUrl=http://example.com')
          .then((response) => {
            expect(response.status).toBe(404)
          })
      })
  })
})
