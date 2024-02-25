package net.caimito.socialblogging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class DataTests {

  @Container
  private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private BlogWatcher blogWatcher;

  @BeforeAll
  static void setUp() {
    mongoDBContainer.start();
    // Additional setup if needed
  }

  @AfterAll
  static void tearDown() {
    mongoDBContainer.stop();
    // Additional teardown if needed
  }

  @BeforeEach
  void init() {
    postsRepository.deleteAll();
  }

  @Test
  void savePostDocument() {
    postsRepository.save(new PostDocument("https://mastodon.social/123", "https://example.com/blog/123",
        SocialMediaServices.ACTIVITY_PUB));

    assertThat(postsRepository.count()).isEqualTo(1);
  }

  @Test
  void findPostDocument() {
    postsRepository.save(new PostDocument("https://mastodon.social/123", "https://example.com/blog/123",
        SocialMediaServices.ACTIVITY_PUB));
    postsRepository.save(new PostDocument("https://mastodon.social/123", "https://example.com/blog/123",
        SocialMediaServices.TWITTER));

    assertThat(postsRepository.findByBlogArticleURL("https://example.com/blog/123")).hasSize(2);
  }

  @Test
  void testBlogWatcher() {
    blogWatcher.watchBlogs();
  }

}
