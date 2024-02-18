import { savePost } from './database.js'

function doPost (articleUrl) {
  console.log('FAKE POST:', articleUrl)
  return Promise.resolve({ postUrl: 'http://activity-pub.com' })
}

function postToActivityPub (articleUrl) {
  console.log('Posting to ActivityPub:', articleUrl)
  return doPost(articleUrl)
    .then((post) => {
      return savePost(post.postUrl, articleUrl, 'activityPub')
    })
    .catch((err) => {
      console.error(err.message)
    })
}

export {
  postToActivityPub
}
