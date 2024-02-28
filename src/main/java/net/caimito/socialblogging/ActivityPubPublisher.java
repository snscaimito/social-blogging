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
    PostDocument post = PostDocumentBuilder.buildPostDocument(item, SocialMediaServices.ACTIVITY_PUB);
    return Optional.of(post);
  }

  @Override
  public SocialMediaServices getService() {
    return SocialMediaServices.ACTIVITY_PUB;
  }

}
