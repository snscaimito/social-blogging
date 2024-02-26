package net.caimito.socialblogging;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends MongoRepository<PostDocument, String> {

  List<PostDocument> findByBlogArticleURL(String url);

  Optional<PostDocument> findByBlogArticleURLAndSocialMediaService(String url, SocialMediaServices service);

}
