package com.socialNetwork.socialNetwork.service;

import com.socialNetwork.socialNetwork.controller.request.CreatePostRequest;
import com.socialNetwork.socialNetwork.controller.request.PostRequest;
import com.socialNetwork.socialNetwork.controller.request.SharePostRequest;
import com.socialNetwork.socialNetwork.controller.response.PostResponse;
import com.socialNetwork.socialNetwork.entities.Post;

import java.util.List;

public interface PostService {
    void createPost(CreatePostRequest postRequest);

    List<PostResponse> getAllPosts(int page);

    List<PostResponse> getMyOwnPosts(int page);

    List<PostResponse> getPostByUserId(String userId, int page);

    void sharePost(SharePostRequest postRequest);

    void deletePost(String postId);
}
