package net.caimito.socialblogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BlogWatcher {
  private static final Logger LOGGER = LoggerFactory.getLogger(BlogWatcher.class);

  @Scheduled(fixedRate = 60000)
  public void watchBlogs() {
    LOGGER.info("Watching blogs...");
  }

}
