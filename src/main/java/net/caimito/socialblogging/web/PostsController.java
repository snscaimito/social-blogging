package net.caimito.socialblogging.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostsController {
  private static final Logger LOGGER = LoggerFactory.getLogger(PostsController.class);

  @GetMapping
  public ResponseEntity<Object> getPost(@RequestParam("articleURL") String articleURL) {
    LOGGER.info("Getting post for articleURL {}", articleURL);
    return ResponseEntity.ok(new PostDetails());
  }

}
