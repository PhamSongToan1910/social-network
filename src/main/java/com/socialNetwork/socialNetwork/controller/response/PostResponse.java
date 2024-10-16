package com.socialNetwork.socialNetwork.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private String id;

    private String userFullName;

    private String avt;

    private String content;

    private String image;

    private int status;

    private String createdAt;

    private int typeOfPost;

    private int countOfReacts;

    private int countOfComments;

    private int countOfShares;

    private int reaction;
}
