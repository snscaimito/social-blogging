package net.caimito.socialblogging;

import java.net.URI;
import java.net.URL;
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
    try {
      URL socialMediaPostURL = URI.create("https://activitypub.example.com/").toURL();
      PostDocument post = PostDocumentBuilder.buildPostDocument(socialMediaPostURL, item,
          SocialMediaServices.ACTIVITY_PUB);
      return Optional.of(post);
    } catch (Exception e) {
      LOGGER.error("Error publishing to ActivityPub", e);
      return Optional.empty();
    }
  }

  @Override
  public SocialMediaServices getService() {
    return SocialMediaServices.ACTIVITY_PUB;
  }

}
