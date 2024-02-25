package net.caimito.socialblogging;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends MongoRepository<PostDocument, String> {

  List<PostDocument> findByBlogArticleURL(String url);

}
