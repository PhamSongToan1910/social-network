package com.socialNetwork.socialNetwork.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReactRequest {
    private int type;

    private int position;

    private String userId;

    private String postId;

    private boolean isLiked;
}
