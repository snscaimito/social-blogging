package net.caimito.socialblogging;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MessageFormatterTest {

  private String loremIpsum = """
      Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla ac nulla sapien. Vestibulum viverra nisl vitae mi ornare, tincidunt pretium nunc tincidunt. Donec accumsan bibendum nibh nec efficitur. Vivamus eu gravida enim. Phasellus vestibulum nibh ipsum, venenatis accumsan est mollis sed. Vestibulum consequat eros eu faucibus dapibus. Vivamus id pretium ipsum, eu rutrum felis. Vivamus eget egestas magna, et pharetra dolor. Sed at enim rutrum, scelerisque velit in, imperdiet lacus. Suspendisse potenti. Quisque facilisis sagittis ligula ut aliquet. Nam sem lectus, dapibus id mauris id, fermentum sollicitudin nibh. Integer sagittis nisi dignissim felis mollis, ac convallis risus molestie.

      Ut sed aliquam felis, ac fermentum nunc. Nullam quis dolor euismod, sollicitudin justo sed, consequat ligula. Morbi posuere libero ac lorem hendrerit venenatis. In a massa non est porta fermentum quis sit amet nibh. Sed vulputate finibus nisi, vitae accumsan dui tincidunt vel. Sed varius fermentum urna sed porttitor. In ullamcorper tempus ante, ut pellentesque erat consectetur vitae. Aenean vel libero enim. Aenean dignissim finibus ante. Aenean eu risus eget magna facilisis laoreet quis ac risus. Phasellus commodo rutrum nulla, et fermentum justo ultrices et. Proin ac eros suscipit, tincidunt urna tempor, sodales velit. Praesent porttitor eu erat a efficitur. Etiam a aliquet ipsum, non dictum est.

      Nunc malesuada libero at tortor lacinia, eu commodo nibh faucibus. Suspendisse vitae tempor sem, eget varius odio. Maecenas ac dolor imperdiet nibh pharetra bibendum non eu ipsum. Quisque posuere in erat non lacinia. Aliquam mattis sodales libero, nec tempor purus sollicitudin ut. Donec molestie sem ligula, efficitur auctor purus vehicula iaculis. Vestibulum ac lorem in lectus pulvinar accumsan. Quisque imperdiet blandit nibh, quis pellentesque risus molestie id. Proin eu orci faucibus, tempor diam vel, iaculis erat. Aenean sollicitudin sed ante et porta. Duis lectus ligula, commodo at blandit eu, tempus vel lorem. Vestibulum sed faucibus enim. Nam congue vehicula arcu, eget euismod arcu rhoncus ut.
        """;

  // Make a Message that contains enough information for the social media post
  // - description from blog post
  // - hashtags from categories
  // - reduce length of description, hashtags per social media service

  @Test
  public void formatForATProto() throws Exception {
    SocialBloggingItem item = new SocialBloggingItem("title", URI.create("https://localhost/article.html").toURL(),
        loremIpsum, List.of("category1", "category2"));

    MessageFormatter formatter = new ATProtoMessageFormatter();

    Message message = formatter.format(item);

    assertThat(message).isNotNull();
    System.out.println(message.getFormattedContent());
    assertThat(message.getFormattedContent())
        .isNotNull()
        .hasSizeLessThanOrEqualTo(ATProtoMessageFormatter.MAX_MESSAGE_LENGTH)
        .contains(item.getArticleURL().toExternalForm())
        .contains("#category1", "#category2");

  }

}
