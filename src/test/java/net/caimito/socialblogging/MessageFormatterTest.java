package net.caimito.socialblogging;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MessageFormatterTest {

  // Make a Message that contains enough information for the social media post
  // - description from blog post
  // - hashtags from categories
  // - reduce length of description, hashtags per social media service

  @Test
  public void testFormatMessage() throws Exception {
    SocialBloggingItem item = new SocialBloggingItem("title", URI.create("https://localhost/article.html").toURL(),
        "description", List.of("category1", "category2"));

    MessageFormatter formatter = new MessageFormatterDefault();

    Message message = formatter.format(item);

    assertThat(message).isNotNull();
    assertThat(message.getFormattedContent()).isNotNull();
  }

}
