package net.caimito.socialblogging;

public class PostDocumentBuilder {

  public static PostDocument buildPostDocument(SocialBloggingItem item, SocialMediaServices service) {
    switch (service) {
      case TWITTER:
        return new PostDocument("http://twitter.com/1", item.getArticleURL().getPath(), service);
      case ACTIVITY_PUB:
        return new PostDocument("http://activitypub.com/1", item.getArticleURL().getPath(), service);
      default:
        throw new IllegalArgumentException("Unknown service " + service);
    }
  }

}
