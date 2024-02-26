package net.caimito.socialblogging;

import java.net.URI;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Component
@Profile("!test")
public class BlogWatcherProduction extends RssReader implements BlogWatcher {
  private static final Logger LOGGER = LoggerFactory.getLogger(BlogWatcherProduction.class);

  public BlogWatcherProduction(PublisherProvider publisherProvider, PostsRepository postsRepository) {
    super(publisherProvider, postsRepository);
  }

  @Scheduled(fixedRate = 60000)
  @Override
  public void watchBlogs() {
    String rssFeedUrl = System.getenv("RSS_FEED");
    if (rssFeedUrl == null) {
      LOGGER.error("RSS_FEED environment variable not present");
      return;
    }

    doReadFeed(rssFeedUrl);
  }

  private void doReadFeed(String rssFeedUrl) {
    try {
      URL feedUrl = URI.create(rssFeedUrl).toURL();

      SyndFeedInput input = new SyndFeedInput();
      SyndFeed feed = input.build(new XmlReader(feedUrl));

      processFeed(feed);
    } catch (Exception e) {
      LOGGER.error("Error reading feed", e);
      throw new RuntimeException(e);
    }
  }

}
