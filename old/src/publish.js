import { postToActivityPub } from './activityPub.js'
import { postToTwitter } from './twitter.js'
import { savePost } from './database.js'

function getSupportedSocialMediaServices() {
  const supportedServices = {}

  if (!process.env.DISABLE_ACTIVITY_PUB) {
    supportedServices.activityPub = postToActivityPub
  }

  if (!process.env.DISABLE_TWITTER) {
    supportedServices.twitter = postToTwitter
  }

  return supportedServices
}

function postArticleToSocialMedia (message) {
  const supportedServices = getSupportedSocialMediaServices()

  return Promise.all(
    Object.keys(supportedServices).map((service) => {
      return supportedServices[service](message)
        .then((post) => {
          return savePost(post.postUrl, message.link, service)
        })
        .catch((err) => {
          console.error(err.message)
        })
    })
  )
}

export {
  postArticleToSocialMedia,
  getSupportedSocialMediaServices
}
