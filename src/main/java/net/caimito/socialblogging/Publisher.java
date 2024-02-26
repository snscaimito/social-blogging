package net.caimito.socialblogging;

import java.util.Optional;

public interface Publisher {

  Optional<PostDocument> publish(SocialBloggingItem item);

}
