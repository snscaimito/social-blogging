import { test } from 'vitest'
import request from 'supertest'
import app from '../src/server.js'

test('GET / with referer', async ({ expect }) => {
  const response = await request(app)
    .get('/')
    .set('referer', 'http://example.com')
  expect(response.status).toBe(200)
  expect(response.text).toContain('http://example.com')
})
