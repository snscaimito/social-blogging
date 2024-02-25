package net.caimito.socialblogging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class BlogWatcherTest {

  @Autowired
  private BlogWatcher blogWatcher;

  @Test
  public void testBlogWatcher() {
    blogWatcher.watchBlogs();
  }

}
