package com.socialNetwork.socialNetwork.repository.interfacePackage;


import com.socialNetwork.socialNetwork.entities.Comment;
import com.socialNetwork.socialNetwork.repository.Repository;

import java.util.List;
import java.util.Map;

public interface CommentRepository extends Repository<Comment> {
    Map<String, Integer> getNumberComments(List<String> postIds);
    Map<String, Comment> getCommentsByPostId(String postId);
}
