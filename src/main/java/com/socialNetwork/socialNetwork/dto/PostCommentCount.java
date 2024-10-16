package com.socialNetwork.socialNetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentCount {
    @Field("_id")
    private String postId;
    private int commentCount;
}
