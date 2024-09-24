package com.socialNetwork.socialNetwork.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteReactRequest {
    private String postId;
    private String userId;
}
