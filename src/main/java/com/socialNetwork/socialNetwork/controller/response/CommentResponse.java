package com.socialNetwork.socialNetwork.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private String id;

    private String userFullName;

    private String avt;

    private String content;

    private String postId;

    private boolean hasSubComment;
}
