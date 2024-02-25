import Parser from 'rss-parser'
import { postArticleToSocialMedia } from './publish.js'

const parser = new Parser({
  customFields: {
    item: [
      ['hashtags', 'hashTags']
    ]
  }
})

function publishFromRss () {
  parser.parseURL(process.env.RSS_FEED_URL)
    .then((feed) => {
      console.log('Publishing feed', feed.title)
      feed.items.forEach(item => {
        console.log('publishing item', item.title)

        const message = {
          link: item.link,
          title: item.title,
          text: item.contentSnippet,
          hashTags: item.hashTags ? item.hashTags.split(',') : []
        }

        return postArticleToSocialMedia(message)
          .catch((err) => {
            console.error(err.message)
          })
      })
      return Promise.resolve()
    })
    .catch((err) => {
      console.error(err.message)
    })
}

export {
  publishFromRss
}
