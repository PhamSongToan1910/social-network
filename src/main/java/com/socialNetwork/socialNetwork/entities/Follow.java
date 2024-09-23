package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.FOLLOW_COLLECTION;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(FOLLOW_COLLECTION)
public class Follow extends BaseEntity{

    public static final String USER_ID = "user_id";
    public static final String USER_ID_FOLLOW = "user_id_follow";

    @Field(USER_ID)
    private String userId;

    @Field(USER_ID_FOLLOW)
    private String userIdFollow;
}
