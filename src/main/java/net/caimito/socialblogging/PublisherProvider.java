package net.caimito.socialblogging;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PublisherProvider {

  private static final List<Publisher> publishers = List.of(
      new ATProtoPublisher(),
      new TwitterPublisher(),
      new ActivityPubPublisher());

  public List<Publisher> getPublishers() {
    return publishers;
  }

}
