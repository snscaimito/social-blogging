package net.caimito.socialblogging;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.junit.jupiter.api.Test;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class RssItemTransformerTest {

  @Test
  public void transformRssItemToSocialBloggingItem() throws Exception {
    URL expectedURL = URI.create("http://www.stephan-schwab.com/2024/02/12/flowing-seasonal-creek.html").toURL();

    InputStream file = getClass().getResourceAsStream("/rss.xml");

    SyndFeedInput input = new SyndFeedInput();
    SyndFeed feed = input.build(new XmlReader(file));

    SocialBloggingItem item = RssItemTransformer.toSocialBloggingItem(feed.getEntries().get(0));
    assertThat(item)
        .isNotNull()
        .extracting(SocialBloggingItem::getTitle,
            SocialBloggingItem::getArticleURL,
            SocialBloggingItem::getDescription)
        .satisfies(extractedValues -> {
          assertThat(extractedValues.get(0)).isEqualTo("Flowing Seasonal Creek");
          assertThat(extractedValues.get(1)).isEqualTo(expectedURL);
          assertThat((String) extractedValues.get(2)).startsWith("After some 50 liters of rain");
        });
    assertThat(item.getDescription()).doesNotContain("  ")
        .doesNotContain("\n");
  }

}
