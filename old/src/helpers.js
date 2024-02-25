export function isDevelopmentMode () {
  return process.env.NODE_ENV === 'development'
}

export function isProductionMode () {
  return process.env.NODE_ENV === 'production'
}

function shortenText (text, length) {
  if (text.length <= length) {
    return text
  }

  let shortened = text.slice(0, length)
  const lastWhitespace = shortened.lastIndexOf(' ')

  if (lastWhitespace > 0) {
    shortened = shortened.substr(0, lastWhitespace)
  }

  return shortened + ' [...]'
}

export function formatMessage (message, characterLimit) {
  let messageText = ''
  const hashTags = message.hashTags ? message.hashTags.map(tag => `#${tag.trim()}`).join(' ') : ''
  const linkText = `${message.link}`
  let messageFooter = ''

  if (hashTags) {
    messageFooter = `\n\n${linkText}\n\n${hashTags}`
  } else {
    messageFooter = `\n\n${linkText}`
  }

  const allowedTextLength = characterLimit - messageFooter.length

  if (message.text.length <= allowedTextLength) {
    messageText = `${message.text}${messageFooter}`
  } else {
    const shortenedText = shortenText(message.text, allowedTextLength)

    messageText = `${shortenedText}${messageFooter}`
  }

  return messageText
}
