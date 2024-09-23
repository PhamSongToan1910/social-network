package com.socialNetwork.socialNetwork.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.SEND_FOLLOW_REQUEST_COLLECTION;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(SEND_FOLLOW_REQUEST_COLLECTION)
public class SendFollow extends BaseEntity {
    public static final String USER_SEND = "user_send";
    public static final String USER_RECEIVER = "user_receiver";
    public static final String IS_ACCEPT = "is_accept";

    @Field(USER_SEND)
    private String userSend;

    @Field(USER_RECEIVER)
    private String userReceiver;

    @Field(IS_ACCEPT)
    private int isAccept;
}
