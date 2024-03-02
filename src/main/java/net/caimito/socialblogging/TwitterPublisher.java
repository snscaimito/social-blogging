package net.caimito.socialblogging;

import java.net.URI;
import java.net.URL;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwitterPublisher implements Publisher {
  private static final Logger LOGGER = LoggerFactory.getLogger(TwitterPublisher.class);

  @Override
  public Optional<PostDocument> publish(SocialBloggingItem item) {
    LOGGER.info("FAKE Publishing to Twitter {}", item);
    try {
      URL socialMediaPostURL = URI.create("https://twitter.example/com").toURL();
      PostDocument post = PostDocumentBuilder.buildPostDocument(socialMediaPostURL, item, SocialMediaServices.TWITTER);
      return Optional.of(post);
    } catch (Exception e) {
      LOGGER.error("Error publishing to Twitter", e);
      return Optional.empty();
    }
  }

  @Override
  public SocialMediaServices getService() {
    return SocialMediaServices.TWITTER;
  }

}
