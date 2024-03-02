package net.caimito.socialblogging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PublisherProviderTest {

  @Autowired
  private PublisherProvider provider;

  @Test
  public void test() {
    assertThat(provider.getPublishers()).isNotEmpty();

    assertThat(provider.getPublishers())
        .isNotEmpty()
        .anySatisfy(publisher -> assertThat(publisher).isInstanceOf(TwitterPublisher.class))
        .anySatisfy(publisher -> assertThat(publisher).isInstanceOf(ActivityPubPublisher.class));
  }

}
