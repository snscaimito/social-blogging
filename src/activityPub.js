import { isDevelopmentMode, formatMessage } from './helpers.js'
import { savePost } from './database.js'

function doPost (message) {
  if (process.env.ENABLE_ACTIVITY_PUB === 'true') {
    const messageText = formatMessage(message, process.env.ACTIVITY_PUB_CHARACTER_LIMIT || 500)

    if (isDevelopmentMode()) {
      console.log('Test mode, skipping post to ACTIVITY_PUB:', messageText)
      return Promise.resolve('Test mode')
    }

    return fetch(process.env.ACTIVITY_PUB_URL, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${process.env.ACTIVITY_PUB_ACCESS_TOKEN}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ status: messageText })
    })
      .then(response => {
        if (!response.ok) {
          return Promise.reject(new Error(`Error: ${response.status}`))
        }
        return response.json()
      })
      .then(data => {
        console.log(data)
        return data
      })
      .catch(error => {
        console.error('Failed to post content:', error)
        return Promise.reject(error)
      })
  } else {
    console.log('Fediverse is disabled')
    return Promise.resolve('Fediverse is disabled')
  }
}

function postToActivityPub (message) {
  console.log('Posting to ActivityPub:', message)
  return doPost(message)
    .then((post) => {
      return savePost(post.postUrl, message.link, 'activityPub')
    })
    .catch((err) => {
      console.error(err.message)
    })
}

export {
  postToActivityPub
}
