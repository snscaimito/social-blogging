import { postToActivityPub } from './activityPub.js'

function postTo (service, articleUrl) {
  if (service === 'activityPub') {
    return postToActivityPub(articleUrl)
  }

  return Promise.reject(new Error(`No such service: ${service}`))
}

export { postTo }
