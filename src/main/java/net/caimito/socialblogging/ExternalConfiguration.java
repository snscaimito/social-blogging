package net.caimito.socialblogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExternalConfiguration implements SmartInitializingSingleton {
  private static final Logger LOGGER = LoggerFactory.getLogger(ExternalConfiguration.class);

  @Value("${RSS_FEED:}")
  private String rssFeed;

  @Value("${ATPROTO_ENABLED:false}")
  private boolean atProtoEnabled;

  @Value("${ATPROTO_HANDLE:}")
  private String atProtoHandle;

  @Value("${ATPROTO_PASSWORD:}")
  private String atProtoPassword;

  @Value("${ATPROTO_PROFILEURL:}")
  private String atProtoProfileUrl;

  public void logState() {
    LOGGER.debug("RSS_FEED: {}", rssFeed);
    LOGGER.debug("ATPROTO_ENABLED: {}", atProtoEnabled);
    LOGGER.debug("ATPROTO_HANDLE: {}", atProtoHandle);
    LOGGER.debug("ATPROTO_PASSWORD: {}", atProtoPassword);
    LOGGER.debug("ATPROTO_PROFILEURL: {}", atProtoProfileUrl);
  }

  public String getRssFeed() {
    return rssFeed;
  }

  public boolean isATProtoEnabled() {
    return atProtoEnabled;
  }

  public String getATProtoHandle() {
    return atProtoHandle;
  }

  public String getATProtoPassword() {
    return atProtoPassword;
  }

  public String getATProtoProfileUrl() {
    return atProtoProfileUrl;
  }

  @Override
  public void afterSingletonsInstantiated() {
    logState();
  }

}
