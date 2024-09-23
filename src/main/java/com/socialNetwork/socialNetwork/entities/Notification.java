package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.NOTIFICATION_COLLECTION;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(NOTIFICATION_COLLECTION)
public class Notification extends BaseEntity{
    public static final String USER_ID_SENDER = "user_id_sender";
    public static final String PLAYER_ID = "player_id";
    public static final String USER_ID_RECEIVER = "user_id_receiver";
    public static final String TYPE = "type";
    public static final String MESSAGE = "message";

    @Field(USER_ID_SENDER)
    private String userIdSender;

    @Field(PLAYER_ID)
    private String playerId;

    @Field(MESSAGE)
    private String message;

    @Field(USER_ID_RECEIVER)
    private String userIdreceiver;

    @Field(TYPE)
    private int type;
}
