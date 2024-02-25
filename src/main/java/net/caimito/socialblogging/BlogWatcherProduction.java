package net.caimito.socialblogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class BlogWatcherProduction implements BlogWatcher {
  private static final Logger LOGGER = LoggerFactory.getLogger(BlogWatcherProduction.class);

  @Scheduled(fixedRate = 60000)
  @Override
  public void watchBlogs() {
    String rssFeedUrl = System.getenv("RSS_FEED");
    if (rssFeedUrl == null) {
      LOGGER.error("RSS_FEED environment variable not present");
      return;
    }

    LOGGER.info("Watching blogs...{}", rssFeedUrl);
  }

}
