package com.socialNetwork.socialNetwork.repository.impl;

import com.socialNetwork.socialNetwork.entities.Post;
import com.socialNetwork.socialNetwork.repository.AbstractRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.PostRepository;
import com.socialNetwork.socialNetwork.utils.Constant;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.POST_COLLECTION;

@Repository
public class PostRepositoryImpl extends AbstractRepository<Post> implements PostRepository {
    public PostRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public Post save(Post entity) {
         return mongoTemplate.save(entity, POST_COLLECTION);
    }

    @Override
    public Map<String, Post> getAllPublicPosts() {
        Map<String, Post> mapPosts = new HashMap<>();
        Query query = new Query(Criteria.where(Post.STATUS).is(Constant.POST.PUBLIC_STATUS));
        query.with(Sort.by(Sort.Direction.DESC, Post.CREATE_AT));
        List<Post> posts = mongoTemplate.find(query, Post.class);
        posts.stream().forEach(post -> {
            mapPosts.put(post.getId().toString(), post);
        });
        return mapPosts;
    }

    @Override
    public List<Post> getAllPostOfFriends(String userId) {
        Query query = new Query(Criteria.where(Post.STATUS).is(Constant.POST.FRIEND_STATUS));
        query.with(Sort.by(Sort.Direction.DESC, Post.CREATE_AT));
        return mongoTemplate.find(query, Post.class);
    }

    @Override
    public List<Post> getAllPostOfFriends(List<String> userIds) {
        List<Post> posts = new ArrayList<>();
        for (String userId : userIds) {
            posts.addAll(getAllPostOfFriends(userId));
        }
        return posts;
    }
}
