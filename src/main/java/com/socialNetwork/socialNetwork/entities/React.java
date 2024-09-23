package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("react")
public class React extends BaseEntity{

    private static final String TYPE = "type";
    private static final String POSITION = "position";
    private static final String USER_ID = "user_id";
    private static final String POST_ID = "post_id";

    @Field(TYPE)
    private int type;

    @Field(POSITION)
    private int position;

    @Field(USER_ID)
    private String userId;

    @Field(POST_ID)
    private String postId;
}
