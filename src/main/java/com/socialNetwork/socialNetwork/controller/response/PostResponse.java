package com.socialNetwork.socialNetwork.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private String userID;

    private String content;

    private String image;

    private int status;

    private String createDateTime;

    private int typeOfPost;
}
