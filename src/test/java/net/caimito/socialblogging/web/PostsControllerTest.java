package net.caimito.socialblogging.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.caimito.socialblogging.PostDocument;
import net.caimito.socialblogging.PostsRepository;
import net.caimito.socialblogging.SocialMediaServices;

@SpringBootTest
@AutoConfigureMockMvc
public class PostsControllerTest {

  @MockBean
  private PostsRepository postsRepository;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void cannotFindPost() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/posts")
        .param("articleURL", "http://www.stephan-schwab.com/2024/02/12/flowing-seasonal-creek.html")
        .param("service", "ACTIVITY_PUB"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void getPostsByArticleURL() throws Exception {
    when(postsRepository.findByBlogArticleURLAndSocialMediaService(any(), any()))
        .thenReturn(java.util.Optional.of(new PostDocument(
            "http://activitypub.com/123",
            "http://www.stephan-schwab.com/2024/02/12/flowing-seasonal-creek.html",
            SocialMediaServices.ACTIVITY_PUB)));

    mockMvc.perform(MockMvcRequestBuilders.get("/posts")
        .param("articleURL", "http://www.stephan-schwab.com/2024/02/12/flowing-seasonal-creek.html")
        .param("service", "ACTIVITY_PUB"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.socialMediaURL").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.socialMediaService").exists());
  }

}
