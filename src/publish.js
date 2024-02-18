import { postToActivityPub } from './activityPub.js'
import { findPost } from './database.js'

function postTo (service, message) {
  const articleUrl = message.link

  return findPost(articleUrl, service)
    .then((post) => {
      if (!post) {
        if (service === 'activityPub') {
          return postToActivityPub(message)
        } else if (service === 'twitter') {
          return Promise.reject(new Error('Twitter not implemented'))
        } else if (service === 'atproto') {
          return Promise.reject(new Error('AT protocol not implemented'))
        } else if (service === 'facebook') {
          return Promise.reject(new Error('Facebook not implemented'))
        } else {
          return Promise.reject(new Error(`No such service: ${service}`))
        }
      } else {
        console.log('already posted', articleUrl)
      }
    })
}

export { postTo }
