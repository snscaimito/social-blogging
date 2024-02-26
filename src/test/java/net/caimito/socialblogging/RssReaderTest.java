package net.caimito.socialblogging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

@ExtendWith(MockitoExtension.class)
public class RssReaderTest {

  @Mock
  private SyndFeed feed;

  @Mock
  private PostsRepository postsRepository;

  @Test
  public void notPublished() {
    when(feed.getEntries()).thenReturn(new ArrayList<>());

    Publisher publisher = new Publisher() {
      @Override
      public Optional<PostDocument> publish(SocialBloggingItem item) {
        return Optional.empty();
      }
    };

    RssReader rssReader = new RssReader(publisher, postsRepository) {
    };

    rssReader.processFeed(feed);

    verify(postsRepository, never()).save(any());
  }

  @Test
  public void successfullyPublished() {
    SyndEntry syndEntry = mock(SyndEntry.class);
    when(syndEntry.getTitle()).thenReturn("Title");
    when(syndEntry.getLink()).thenReturn("http://example.com/1");

    when(feed.getEntries()).thenReturn(List.of(syndEntry));

    Publisher publisher = new Publisher() {
      @Override
      public Optional<PostDocument> publish(SocialBloggingItem item) {
        return Optional.of(new PostDocument(null, null, null));
      }
    };

    RssReader rssReader = new RssReader(publisher, postsRepository) {
    };

    rssReader.processFeed(feed);

    verify(postsRepository).save(any());
  }

}
