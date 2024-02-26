package net.caimito.socialblogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rometools.rome.feed.synd.SyndFeed;

public abstract class RssReader {
  private static final Logger LOGGER = LoggerFactory.getLogger(RssReader.class);
  private Publisher publisher;
  private PostsRepository postsRepository;

  public RssReader(Publisher publisher, PostsRepository postsRepository) {
    this.publisher = publisher;
    this.postsRepository = postsRepository;
  }

  public void processFeed(SyndFeed feed) {
    assert feed != null;

    feed.getEntries().forEach(entry -> {
      SocialBloggingItem item = RssItemTransformer.toSocialBloggingItem(entry);
      LOGGER.debug("Item {}", item);

      publisher.publish(item).ifPresent(postDocument -> {
        postsRepository.save(postDocument);
      });

    });
  }

}
