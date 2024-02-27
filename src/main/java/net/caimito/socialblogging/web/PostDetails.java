package net.caimito.socialblogging.web;

import net.caimito.socialblogging.SocialMediaServices;

public class PostDetails {

  private String socialMediaURL = "http://activitypub.com/123";
  private SocialMediaServices socialMediaService = SocialMediaServices.ACTIVITY_PUB;

  public SocialMediaServices getSocialMediaService() {
    return socialMediaService;
  }

  public String getSocialMediaURL() {
    return socialMediaURL;
  }

}
