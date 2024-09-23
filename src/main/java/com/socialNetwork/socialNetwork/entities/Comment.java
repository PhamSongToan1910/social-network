package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("comment")
public class Comment extends BaseEntity{

    private static final String ID_PARENT = "id_parent";
    private static final String USER_ID = "user_id";
    private static final String CONTENT = "content";
    private static final String POST_ID = "post_id";


    @Field(ID_PARENT)
    private String idParent;

    @Field(USER_ID)
    private String userId;

    @Field(CONTENT)
    private String content;

    @Field(POST_ID)
    private String postId;
}
