package net.caimito.socialblogging;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublisherProvider {

  @Autowired
  private ATProtoPublisher atProtoPublisher;

  @Autowired
  private TwitterPublisher twitterPublisher;

  @Autowired
  private ActivityPubPublisher activityPubPublisher;

  public List<Publisher> getPublishers() {
    return List.of(
        atProtoPublisher,
        twitterPublisher,
        activityPubPublisher);
  }

}
