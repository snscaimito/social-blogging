package net.caimito.socialblogging.web;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.caimito.socialblogging.PostDocument;
import net.caimito.socialblogging.PostsRepository;
import net.caimito.socialblogging.SocialMediaServices;

@RestController
@RequestMapping("/posts")
public class PostsController {
  private static final Logger LOGGER = LoggerFactory.getLogger(PostsController.class);

  @Autowired
  private PostsRepository postsRepository;

  @GetMapping
  public ResponseEntity<Object> getPost(@RequestParam("articleURL") String articleURL,
      @RequestParam("service") String service) {
    LOGGER.info("Getting post for articleURL {}", articleURL);
    Optional<PostDocument> post = postsRepository.findByBlogArticleURLAndSocialMediaService(articleURL,
        SocialMediaServices.valueOf(service));
    if (post.isPresent()) {
      PostDocument postDocument = post.get();
      return ResponseEntity.ok(new PostDetails(postDocument.getBlogArticleURL(),
          postDocument.getSocialMediaService(), postDocument.getSocialMediaPostURL()));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
