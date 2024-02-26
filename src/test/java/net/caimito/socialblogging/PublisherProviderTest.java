package net.caimito.socialblogging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PublisherProviderTest {

  @Test
  public void test() {
    PublisherProvider provider = new PublisherProvider();
    assertThat(provider.getPublishers()).isNotEmpty();

    assertThat(provider.getPublishers())
        .isNotEmpty()
        .anySatisfy(publisher -> assertThat(publisher).isInstanceOf(TwitterPublisher.class))
        .anySatisfy(publisher -> assertThat(publisher).isInstanceOf(ActivityPubPublisher.class));
  }

}
