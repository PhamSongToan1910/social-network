package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.POST_COLLECTION;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(POST_COLLECTION)
public class Post extends BaseEntity{

    public static final String USER_ID = "user_id";
    public static final String CONTENT = "content";
    public static final String IMAGE = "image";
    public static final String STATUS = "status";
    public static final String TYPE_OF_POST = "type_of_post";

    @Field(USER_ID)
    private String userID;

    @Field(CONTENT)
    private String content;

    @Field(IMAGE)
    private String image;

    @Field(STATUS)
    private int status;

    @Field(TYPE_OF_POST)
    private int typeOfPost;
}
