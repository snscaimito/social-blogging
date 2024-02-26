package net.caimito.socialblogging;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterPublisher implements Publisher {
  private static final Logger LOGGER = LoggerFactory.getLogger(TwitterPublisher.class);

  @Override
  public Optional<PostDocument> publish(SocialBloggingItem item) {
    LOGGER.info("FAKE Publishing to Twitter {}", item);
    return Optional.empty();
  }

}
