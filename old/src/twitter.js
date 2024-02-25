import { isProductionMode, formatMessage } from './helpers.js'

function postToTwitter (message) {
  const postResult = {
    postUrl: 'http://localhost/testMode'
  }

  const messageText = formatMessage(message, process.env.TWITTER_CHARACTERS_LIMIT || 280)

  if (!isProductionMode()) {
    console.log('Test mode, skipping post to Twitter:', messageText)
    return Promise.resolve(postResult)
  }

  if (process.env.DISABLE_TWITTER) {
    console.log('Twitter is disabled')
    return Promise.resolve('Twitter is disabled')
  }
}

export {
  postToTwitter
}
