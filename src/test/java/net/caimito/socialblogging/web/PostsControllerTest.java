package net.caimito.socialblogging.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PostsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetPostsByArticleURL() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/posts")
        .param("articleURL", "http://www.stephan-schwab.com/2024/02/12/flowing-seasonal-creek.html"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.socialMediaURL").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.socialMediaService").exists());
  }

}
