import { test } from 'vitest'
import { setupDatabase, addComment, findComments } from '../src/database.js'

test('addComment', async ({ expect }) => {
  await setupDatabase()

  const result = await addComment('Hello!', 'http://example.com')
  expect(result).toStrictEqual(expect.objectContaining({
    changes: 1
  }))
})

test('find comments', async ({ expect }) => {
  await setupDatabase()
  await addComment('Hello!', 'http://example.com')

  const comments = await findComments('http://example.com')
  expect(comments.length).toBe(1)
})
