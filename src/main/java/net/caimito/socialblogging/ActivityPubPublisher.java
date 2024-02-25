package net.caimito.socialblogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActivityPubPublisher implements Publisher {
  private static final Logger LOGGER = LoggerFactory.getLogger(ActivityPubPublisher.class);

  @Override
  public void publish(SocialBloggingItem item) {
    LOGGER.info("Publishing to ActivityPub {}", item);
  }

}
