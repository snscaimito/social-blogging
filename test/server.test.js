import './setupTests.js'
import { describe, test } from 'vitest'
import request from 'supertest'
import app from '../src/server.js'

describe('server', () => {
  test('GET /posted-on/:service', async ({ expect }) => {
    const response = await request(app)
      .get('/posted-on/twitter')
    expect(response.status).toBe(200)
    expect(response.body.postUrl).toBe('http://example.com')
  })
})
