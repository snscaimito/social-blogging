package net.caimito.socialblogging;

import java.net.URI;
import java.net.URL;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import bsky4j.BlueskyFactory;
import bsky4j.api.entity.atproto.server.ServerCreateSessionRequest;
import bsky4j.api.entity.atproto.server.ServerCreateSessionResponse;
import bsky4j.api.entity.bsky.feed.FeedPostRequest;
import bsky4j.api.entity.bsky.feed.FeedPostResponse;
import bsky4j.api.entity.share.Response;
import bsky4j.domain.Service;

@Component
public class ATProtoPublisher implements Publisher {
  private static final Logger LOGGER = LoggerFactory.getLogger(ATProtoPublisher.class);

  private ExternalConfiguration externalConfiguration;

  public ATProtoPublisher(ExternalConfiguration externalConfiguration) {
    this.externalConfiguration = externalConfiguration;
  }

  @Override
  public SocialMediaServices getService() {
    return SocialMediaServices.AT_PROTO;
  }

  @Override
  public Optional<PostDocument> publish(SocialBloggingItem item) {
    if (!externalConfiguration.isATProtoEnabled()) {
      LOGGER.info("AT Proto is not enabled");
      return Optional.empty();
    }

    try {
      Response<ServerCreateSessionResponse> response = BlueskyFactory
          .getInstance(Service.BSKY_SOCIAL.getUri())
          .server().createSession(
              ServerCreateSessionRequest.builder()
                  .identifier(externalConfiguration.getATProtoHandle())
                  .password(externalConfiguration.getATProtoPassword())
                  .build());

      String accessJwt = response.get().getAccessJwt();

      Response<FeedPostResponse> feedResponse = BlueskyFactory
          .getInstance(Service.BSKY_SOCIAL.getUri())
          .feed().post(
              FeedPostRequest.builder()
                  .accessJwt(accessJwt)
                  .text("Hello World!!") // TODO make real
                  .build());

      String uri = feedResponse.get().getUri();
      LOGGER.debug("URI: {}", uri);

      String postId = uri.substring(uri.lastIndexOf("app.bsky.feed.post") + "app.bsky.feed.post".length() + 1);
      String url = externalConfiguration.getATProtoProfileUrl() + "/post/" + postId;
      LOGGER.debug("URL: {}", url);

      URL socialMediaPostURL = URI.create(url).toURL();
      PostDocument post = PostDocumentBuilder.buildPostDocument(socialMediaPostURL, item, SocialMediaServices.AT_PROTO);
      return Optional.of(post);
    } catch (Exception e) {
      LOGGER.error("Error publishing to AT Proto", e);
      return Optional.empty();
    }
  }

}
