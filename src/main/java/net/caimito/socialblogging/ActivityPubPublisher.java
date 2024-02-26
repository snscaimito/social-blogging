package net.caimito.socialblogging;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActivityPubPublisher implements Publisher {
  private static final Logger LOGGER = LoggerFactory.getLogger(ActivityPubPublisher.class);

  @Override
  public Optional<PostDocument> publish(SocialBloggingItem item) {
    LOGGER.info("FAKE Publishing to ActivityPub {}", item);
    return Optional.empty();
  }

}
