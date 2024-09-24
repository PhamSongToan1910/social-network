package com.socialNetwork.socialNetwork.entities;

import com.socialNetwork.socialNetwork.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("react")
public class React extends BaseEntity{

    public static final String TYPE = "type";
    public static final String POSITION = "position";
    public static final String USER_ID = "user_id";
    public static final String POST_ID = "post_id";
    public static final String IS_REACTED = "is_reacted";

    @Field(TYPE)
    private int type;

    @Field(POSITION)
    private int position;

    @Field(USER_ID)
    private String userId;

    @Field(POST_ID)
    private String postId;

    @Field(IS_REACTED)
    private Map<Integer, Boolean> isReacted = Map.ofEntries(
            entry(Constant.REACT.LIKE, false),
            entry(Constant.REACT.LOVE, false),
            entry(Constant.REACT.CARE, false),
            entry(Constant.REACT.WOW, false),
            entry(Constant.REACT.HAHA, false),
            entry(Constant.REACT.SAD, false),
            entry(Constant.REACT.ANGRY, false)
    );
}
