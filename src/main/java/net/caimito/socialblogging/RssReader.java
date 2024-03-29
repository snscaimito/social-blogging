package net.caimito.socialblogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rometools.rome.feed.synd.SyndFeed;

public abstract class RssReader {
  private static final Logger LOGGER = LoggerFactory.getLogger(RssReader.class);
  private PublisherProvider publisherProvider;
  private PostsRepository postsRepository;

  public RssReader(PublisherProvider publisherProvider, PostsRepository postsRepository) {
    this.publisherProvider = publisherProvider;
    this.postsRepository = postsRepository;
  }

  public void processFeed(SyndFeed feed) {
    assert feed != null;

    feed.getEntries().forEach(entry -> {
      SocialBloggingItem item = RssItemTransformer.toSocialBloggingItem(entry);
      LOGGER.debug("Item {}", item);

      publisherProvider.getPublishers().forEach(publisher -> {
        postsRepository
            .findByBlogArticleURLAndSocialMediaService(item.getArticleURL().getPath(), publisher.getService())
            .ifPresentOrElse(post -> LOGGER.debug("Post already published {}", post),
                () -> publisher.publish(item).ifPresentOrElse(post -> {
                  LOGGER.debug("Published {}", post);
                  postsRepository.save(post);
                }, () -> LOGGER.debug("Not published {}", item)));
      });
    });
  }

}
