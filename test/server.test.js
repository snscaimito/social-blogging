import './setupTests.js'
import { describe, test } from 'vitest'
import request from 'supertest'
import app from '../src/server.js'

describe('server', () => {
  test('GET / with referer', async ({ expect }) => {
    const response = await request(app)
      .get('/')
      .set('referer', 'http://example.com')
    expect(response.status).toBe(200)
  })

  test('POST /comment', async ({ expect }) => {
    const response = await request(app)
      .post('/comment')
      .send({ comment: 'Hello!' })
    expect(response.status).toBe(302)
  })
})
