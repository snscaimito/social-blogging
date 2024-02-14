import { test } from 'vitest'
import request from 'supertest'
import app from '../src/server.js'

test('GET /', async ({ expect }) => {
  const response = await request(app).get('/')
  expect(response.status).toBe(200)
  expect(response.text).toBe('Hello, World!')
})
