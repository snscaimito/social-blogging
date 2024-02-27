package net.caimito.socialblogging;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class PostDocument {

  @Id
  private ObjectId id;

  @Indexed
  private String blogArticleURL;

  private String socialMediaPostURL;
  private SocialMediaServices socialMediaService;

  public PostDocument(String socialMediaPostURL, String blogArticleURL, SocialMediaServices socialMediaService) {
    this.socialMediaPostURL = socialMediaPostURL;
    this.blogArticleURL = blogArticleURL;
    this.socialMediaService = socialMediaService;
  }

  public String getSocialMediaPostURL() {
    return socialMediaPostURL;
  }

  public String getBlogArticleURL() {
    return blogArticleURL;
  }

  public SocialMediaServices getSocialMediaService() {
    return socialMediaService;
  }

  @Override
  public String toString() {
    return "PostDocument [id=" + id + ", blogArticleURL=" + blogArticleURL + ", socialMediaService="
        + socialMediaService + "]";
  }

}
