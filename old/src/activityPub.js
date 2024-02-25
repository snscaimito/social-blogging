import { isProductionMode, formatMessage } from './helpers.js'

function postToActivityPub (message) {
  const postResult = {
    postUrl: 'http://localhost/testMode'
  }

  const messageText = formatMessage(message, process.env.ACTIVITY_PUB_CHARACTER_LIMIT || 500)

  if (!isProductionMode()) {
    console.log('Test mode, skipping post to ACTIVITY_PUB:', messageText)
    return Promise.resolve(postResult)
  }

  if (!process.env.ENABLE_ACTIVITY_PUB) {
    console.log('Fediverse is disabled')
    return Promise.resolve('Fediverse is disabled')
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
      // TODO return URL of the post
      return data
    })
    .catch(error => {
      console.error('Failed to post content:', error)
      return Promise.reject(error)
    })
}

export {
  postToActivityPub
}
