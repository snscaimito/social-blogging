package net.caimito.socialblogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rometools.rome.feed.synd.SyndFeed;

public abstract class RssReader {
  private static final Logger LOGGER = LoggerFactory.getLogger(RssReader.class);

  @Autowired
  private Publisher publisher;

  public void processFeed(SyndFeed feed) {
    feed.getEntries().forEach(entry -> {
      SocialBloggingItem item = RssItemTransformer.toSocialBloggingItem(entry);
      LOGGER.debug("Item {}", item);

      publisher.publish(item);
    });
  }

}
