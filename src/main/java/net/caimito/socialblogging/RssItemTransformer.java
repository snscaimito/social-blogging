package net.caimito.socialblogging;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import com.rometools.rome.feed.synd.SyndEntry;

public class RssItemTransformer {

  public static SocialBloggingItem toSocialBloggingItem(SyndEntry syndEntry) {
    assert syndEntry != null;
    assert syndEntry.getTitle() != null;
    assert syndEntry.getLink() != null;

    try {
      String description = syndEntry.getDescription() == null ? ""
          : syndEntry.getDescription().getValue()
              .replaceAll("\r\n", "")
              .replaceAll("\\s+", " ");

      URL url = URI.create(syndEntry.getLink()).toURL();

      return new SocialBloggingItem(syndEntry.getTitle(),
          url, description,
          syndEntry.getCategories().stream().map(category -> category.getName()).toList());
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

}
