package net.caimito.socialblogging;

import java.net.URL;
import java.util.List;

public class SocialBloggingItem {

  private String title;
  private URL articleURL;
  private String description;
  private List<String> categories;

  public SocialBloggingItem(String title, URL articleURL, String description, List<String> categories) {
    this.title = title;
    this.articleURL = articleURL;
    this.description = description;
    this.categories = categories;
  }

  public String getTitle() {
    return title;
  }

  public URL getArticleURL() {
    return articleURL;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getCategories() {
    return categories;
  }

}
