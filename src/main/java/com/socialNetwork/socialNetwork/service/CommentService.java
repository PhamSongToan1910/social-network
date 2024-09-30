package com.socialNetwork.socialNetwork.service;

import com.socialNetwork.socialNetwork.controller.request.AddCommentRequest;
import com.socialNetwork.socialNetwork.controller.request.AddSubCommentRequest;

public interface CommentService {
    void addComment(AddCommentRequest comment);

    void addSubComment(AddCommentRequest comment);
}
