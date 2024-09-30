package com.socialNetwork.socialNetwork.service.impl;

import com.socialNetwork.socialNetwork.controller.request.AddCommentRequest;
import com.socialNetwork.socialNetwork.entities.Comment;
import com.socialNetwork.socialNetwork.entities.Post;
import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.interfacePackage.CommentRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.PostRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.UserRepository;
import com.socialNetwork.socialNetwork.service.CommentService;
import com.socialNetwork.socialNetwork.service.NotificationService;
import com.socialNetwork.socialNetwork.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NotificationService notificationService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void addComment(AddCommentRequest addCommentRequest) {
        Comment comment = castToComment(addCommentRequest);
        Comment commentSaved = commentRepository.save(comment);
        if(commentSaved != null) {
            Post post = postRepository.findById(addCommentRequest.getPostId(), Post.class);
            User userCreatePost = userRepository.findById(post.getUserID(), User.class);
            User userSendComment = userRepository.findById(addCommentRequest.getUserId(), User.class);

            notificationService.sendMessageToUser(userSendComment.getId().toString(), userSendComment.getFullName() + " " + Constant.COMMENT_POST, userCreatePost.getId().toString());

        }
    }

    @Override
    public void addSubComment(AddCommentRequest addCommentRequest) {
        Comment commentParent = commentRepository.findById(addCommentRequest.getIdParent(), Comment.class);
        Set<String> idCommentParent = commentParent.getIdParent();
        idCommentParent.add(addCommentRequest.getIdParent());
        Comment comment = new Comment();
        comment.setContent(addCommentRequest.getContent());
        comment.setPostId(addCommentRequest.getPostId());
        comment.setUserId(addCommentRequest.getUserId());
        comment.setCreatedBy(addCommentRequest.getUserId());
        comment.getIdParent().addAll(idCommentParent);
        Comment commentSaved = commentRepository.save(comment);

        if(commentSaved != null) {
            Post post = postRepository.findById(addCommentRequest.getPostId(), Post.class);
            User userCreatePost = userRepository.findById(post.getUserID(), User.class);
            User userSendComment = userRepository.findById(addCommentRequest.getUserId(), User.class);

            notificationService.sendMessageToUser(userSendComment.getId().toString(), userSendComment.getFullName() + " " + Constant.REPPLY_COMMENT_POST, userCreatePost.getId().toString());

        }
    }

    private Comment castToComment(AddCommentRequest addCommentRequest) {
        Comment comment = new Comment();
        comment.setContent(addCommentRequest.getContent());
        comment.setPostId(addCommentRequest.getPostId());
        comment.setUserId(addCommentRequest.getUserId());
        comment.setCreatedBy(addCommentRequest.getUserId());
        return comment;
    }
}
