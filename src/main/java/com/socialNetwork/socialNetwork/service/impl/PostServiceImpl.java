package com.socialNetwork.socialNetwork.service.impl;

import com.socialNetwork.socialNetwork.controller.request.CreatePostRequest;
import com.socialNetwork.socialNetwork.controller.request.SharePostRequest;
import com.socialNetwork.socialNetwork.controller.response.PostResponse;
import com.socialNetwork.socialNetwork.entities.Follow;
import com.socialNetwork.socialNetwork.entities.Post;
import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.interfacePackage.FollowRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.PostRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.UserRepository;
import com.socialNetwork.socialNetwork.service.FollowService;
import com.socialNetwork.socialNetwork.service.NotificationService;
import com.socialNetwork.socialNetwork.service.PostService;
import com.socialNetwork.socialNetwork.utils.Constant;
import com.socialNetwork.socialNetwork.utils.ModelMapperUtils;
import com.socialNetwork.socialNetwork.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final MongoTemplate mongoTemplate;
    private final PostRepository postRepository;
    private final FollowService followService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public static final Map<String, Post> MAP_ALL_POSTS = new ConcurrentHashMap<>();

    @Override
    public void createPost(CreatePostRequest postRequest) {
        Post post = castCreatePostRequestToPost(postRequest);
        assert post != null;
        MAP_ALL_POSTS.put(post.getId().toString(), post);
        postRepository.save(post);

        if (post.getStatus() >= Constant.POST.FRIEND_STATUS) {
            User user = userRepository.findById(post.getUserID(), User.class);
            List<String> userFollowerIds = followService.getFollowerByUserId(post.getUserID());
            notificationService.sendMessageToAllUsers(post.getUserID(), user.getFullName() + " " + Constant.CREATE_POST, userFollowerIds);
        }
    }

    @Override
    public List<PostResponse> getAllPosts(int page) {
        int limit = Constant.PAGE_SIZE;
        int skip = (page - 1) * limit;
        List<PostResponse> postResponseList = new ArrayList<>();

        List<Post> posts = new ArrayList<>(MAP_ALL_POSTS.values());

        if (skip < posts.size()) {
            posts = posts.stream()
                    .skip(skip)
                    .limit(limit).toList();

            for (Post post : posts) {
                postResponseList.add(ModelMapperUtils.toObject(post, PostResponse.class));
            }
        }

        return postResponseList;
    }

    @Override
    public List<PostResponse> getMyOwnPosts(int page) {
        User user = Utils.getCurrentUser();
        int limit = Constant.PAGE_SIZE;
        int skip = (page - 1) * limit;
        List<PostResponse> postResponseList = new ArrayList<>();

        List<Post> posts = new ArrayList<>(MAP_ALL_POSTS.values());

        if (skip < posts.size()) {
            posts = posts.stream().filter(post -> post.getUserID().equals(user.getId().toString()))
                    .skip(skip)
                    .limit(limit).toList();

            for (Post post : posts) {
                postResponseList.add(ModelMapperUtils.toObject(post, PostResponse.class));
            }
        }

        return postResponseList;
    }

    @Override
    public List<PostResponse> getPostByUserId(String userId, int page) {
        User user = Utils.getCurrentUser();
        int limit = Constant.PAGE_SIZE;
        int skip = (page - 1) * limit;
        List<PostResponse> postResponseList = new ArrayList<>();

        List<Post> posts = new ArrayList<>(MAP_ALL_POSTS.values());

        if (followRepository.getFollowerByUserId(userId).contains(user.getId().toString())) {
            if (skip < posts.size()) {
                posts = posts.stream().filter(post -> post.getUserID().equals(user.getId().toString()) && post.getStatus() >= Constant.POST.FRIEND_STATUS)
                        .skip(skip)
                        .limit(limit).toList();

                for (Post post : posts) {
                    postResponseList.add(ModelMapperUtils.toObject(post, PostResponse.class));
                }
            } else {
                posts = posts.stream().filter(post -> post.getUserID().equals(user.getId().toString()) && post.getStatus() == Constant.POST.PUBLIC_STATUS)
                        .skip(skip)
                        .limit(limit).toList();

                for (Post post : posts) {
                    postResponseList.add(ModelMapperUtils.toObject(post, PostResponse.class));
                }
            }
        }

        return postResponseList;
    }

    @Override
    public void sharePost(SharePostRequest postRequest) {
        Post post = castSharePostRequestToPost(postRequest);
        assert post != null;
        MAP_ALL_POSTS.put(post.getId().toString(), post);
        postRepository.save(post);

        if (post.getStatus() >= Constant.POST.FRIEND_STATUS) {
            User user = userRepository.findById(post.getUserID(), User.class);
            List<String> userFollowerIds = followService.getFollowerByUserId(post.getUserID());
            notificationService.sendMessageToAllUsers(post.getUserID(), user.getFullName() + " " + Constant.SHARE_POST, userFollowerIds);
        }
    }

    @Override
    public void deletePost(String postId) {
        Post post = postRepository.findById(postId, Post.class);
        ObjectId ObjPostId = new ObjectId(postId);
        Query query = new Query(Criteria.where(Post._ID).is(ObjPostId));
        Update update = new Update().set(Post.IS_DELETE, true)
                .set(Post.LAST_MODIFIED_AT, Utils.convertDateToString(new Date()))
                .set(Post.LAST_MODIFIED_BY, post.getUserID());

        MAP_ALL_POSTS.remove(postId);

        mongoTemplate.updateFirst(query, update, Post.class);

        Query findQuery = new Query(Criteria.where(Post.IMAGE).is(postId));
        findQuery.addCriteria(Criteria.where(Post.TYPE_OF_POST).is(Constant.TYPE_POST.SHARE_POST));
        List<Post> listPost = mongoTemplate.find(findQuery, Post.class);
        listPost.forEach(id -> MAP_ALL_POSTS.remove(id.toString()));

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Post.class);
        for (Post pst : listPost) {
            Query find = new Query().addCriteria(new Criteria(Post._ID).is(pst.getId()));
            Update delete = new Update().set(Post.IS_DELETE, true)
                    .set(Post.LAST_MODIFIED_AT, Utils.convertDateToString(new Date()))
                    .set(Post.LAST_MODIFIED_BY, post.getUserID());
            bulkOperations.updateOne(find, delete);
        }
        bulkOperations.execute();
    }

    private Post castCreatePostRequestToPost(CreatePostRequest postRequest) {
        try {
            Post post = new Post();
            post.setImage(Arrays.toString(postRequest.getImage().getBytes()));
            post.setContent(postRequest.getContent());
            post.setUserID(postRequest.getUserID());
            post.setStatus(postRequest.getStatus());
            post.setCreatedBy(postRequest.getUserID());
            post.setTypeOfPost(Constant.TYPE_POST.CREATE_POST);
            return post;
        } catch (Exception e) {
            log.info("exception: {}", e);
        }
        return null;
    }

    private Post castSharePostRequestToPost(SharePostRequest postRequest) {
        try {
            Post post = new Post();
            post.setImage(postRequest.getSharedPostId());
            post.setContent(postRequest.getContent());
            post.setUserID(postRequest.getUserID());
            post.setStatus(postRequest.getStatus());
            post.setCreatedBy(postRequest.getUserID());
            post.setTypeOfPost(Constant.TYPE_POST.CREATE_POST);
            return post;
        } catch (Exception e) {
            log.info("exception: {}", e);
        }
        return null;
    }


    public void init(String username) {
        MAP_ALL_POSTS.clear();
        System.out.println("username: " + username);
        User user = userRepository.findByUsername(username);
        MAP_ALL_POSTS.putAll(postRepository.getAllPublicPosts());
        List<Follow> listFriendIds = followRepository.getFollowByUserId(user.getId().toString());
        List<String> listStringFriendIds = listFriendIds.stream().map(follow -> follow.getId().toString()).toList();
        List<Post> postOfFriends = postRepository.getAllPostOfFriends(listStringFriendIds);
        postOfFriends.forEach(post -> {
            MAP_ALL_POSTS.put(post.getUserID(), post);
        });
    }
}
