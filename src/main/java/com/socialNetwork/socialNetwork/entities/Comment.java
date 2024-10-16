package com.socialNetwork.socialNetwork.entities;

import com.socialNetwork.socialNetwork.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(Constant.COLLECTION.COMMENT_COLLECTION)
public class Comment extends BaseEntity{

    public static final String ID_PARENT = "id_parent";
    public static final String USER_ID = "user_id";
    public static final String CONTENT = "content";
    public static final String POST_ID = "post_id";


    @Field(ID_PARENT)
    private List<String> idParent = new ArrayList<>();

    @Field(USER_ID)
    private String userId;

    @Field(CONTENT)
    private String content;

    @Field(POST_ID)
    private String postId;
}
