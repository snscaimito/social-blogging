package net.caimito.socialblogging;

import java.util.Optional;

public interface Publisher {

  SocialMediaServices getService();

  Optional<PostDocument> publish(SocialBloggingItem item);

}
