package net.caimito.socialblogging;

public class MessageFormatterDefault implements MessageFormatter {

  @Override
  public Message format(SocialBloggingItem item) {
    return new Message() {
      @Override
      public String getFormattedContent() {
        return item.getDescription();
      }
    };
  }

}
