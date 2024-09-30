package com.socialNetwork.socialNetwork.controller;

import com.socialNetwork.socialNetwork.controller.request.AddCommentRequest;
import com.socialNetwork.socialNetwork.controller.request.DeleteCommentRequest;
import com.socialNetwork.socialNetwork.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/comment/{postId}")
    public void sendComment(@DestinationVariable String postId, @Payload AddCommentRequest message) {
        commentService.addComment(message);
        messagingTemplate.convertAndSend("/topic/comments/post/" + postId, message.getContent());
    }

    @MessageMapping("/comment/add-subcomment/{postId}")
    public void sendSubComment(@DestinationVariable String postId, AddCommentRequest message) {
        commentService.addSubComment(message);
        messagingTemplate.convertAndSend("/topic/comments/post/" + postId, message.getContent());
    }

    @MessageMapping("/comment/delete-comment/{postId}")
    public void sendChatMessage(@DestinationVariable String postId, DeleteCommentRequest message) {
        messagingTemplate.convertAndSend("/topic/chat/room/" + postId, message);
    }
}
