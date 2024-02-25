package net.caimito.socialblogging;

import java.net.MalformedURLException;
import java.net.URI;

import com.rometools.rome.feed.synd.SyndEntry;

public class RssItemTransformer {

  public static SocialBloggingItem toSocialBloggingItem(SyndEntry syndEntry) {
    try {
      String description = syndEntry.getDescription().getValue()
          .replaceAll("\r\n", "")
          .replaceAll("\\s+", " ");

      return new SocialBloggingItem(syndEntry.getTitle(),
          URI.create(syndEntry.getLink()).toURL(),
          description,
          syndEntry.getCategories().stream().map(category -> category.getName()).toList());
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

}
