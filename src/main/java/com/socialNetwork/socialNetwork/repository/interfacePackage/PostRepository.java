package com.socialNetwork.socialNetwork.repository.interfacePackage;

import com.socialNetwork.socialNetwork.entities.Post;
import com.socialNetwork.socialNetwork.repository.Repository;

import java.util.List;
import java.util.Map;

public interface PostRepository extends Repository<Post> {
    Map<String, Post> getAllPublicPosts();

    List<Post> getAllPostOfFriends(String userId);

    List<Post> getAllPostOfFriends(List<String> userIds);

}
