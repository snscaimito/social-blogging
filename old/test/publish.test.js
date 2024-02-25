import dotenv from 'dotenv'
import { beforeEach, describe, test, expect } from 'vitest'
import { setupDatabase, findPost } from '../src/database.js'
import { postArticleToSocialMedia, getSupportedSocialMediaServices } from '../src/publish.js'

dotenv.config({
  path: process.env.NODE_ENV === 'test' ? '.env.test' : '.env'
})

describe.only('publishing', () => {
  beforeEach(async () => {
    await setupDatabase()
  })

  test('post article to social media', async () => {
    const message = {
      link: 'https://example.com/article',
      title: 'Example Article',
      text: 'This is the article content',
      hashTags: ['example', 'article']
    }

    await postArticleToSocialMedia(message)

    Object.keys(getSupportedSocialMediaServices()).forEach(async (service) => {
      const result = await findPost(message.link, service)
      console.log('result', result)
      expect(result).toBeDefined()
    })
  })
})
