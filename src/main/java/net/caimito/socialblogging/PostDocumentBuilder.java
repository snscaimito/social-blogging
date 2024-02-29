package net.caimito.socialblogging;

import java.net.URL;

public class PostDocumentBuilder {

  public static PostDocument buildPostDocument(URL socialMediaPostURL, SocialBloggingItem item,
      SocialMediaServices service) {
    return new PostDocument(socialMediaPostURL.toExternalForm(), item.getArticleURL().getPath(), service);
  }

}
