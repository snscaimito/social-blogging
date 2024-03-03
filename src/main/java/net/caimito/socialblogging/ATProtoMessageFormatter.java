package net.caimito.socialblogging;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ATProtoMessageFormatter implements MessageFormatter {
  public static final int MAX_MESSAGE_LENGTH = 300;

  @Override
  public Message format(SocialBloggingItem item) {
    String linkText = item.getArticleURL().toExternalForm();
    String hashtags = String.join(" ", addHashtag(item.getCategories()));
    String descriptionEllipse = " [...]";
    int remainingLength = MAX_MESSAGE_LENGTH -
        (linkText.length() + 1) -
        (hashtags.length() + 1) -
        descriptionEllipse.length();

    String messageText = String.format("%s%s\n%s\n%s", item.getDescription().substring(0, remainingLength),
        descriptionEllipse, linkText, hashtags);

    return new Message() {
      @Override
      public String getFormattedContent() {
        return messageText;
      }
    };
  }

  public List<String> addHashtag(List<String> tags) {
    List<String> result = new ArrayList<String>();
    for (String tag : tags) {
      result.add("#" + tag);
    }
    return result;
  }

}
