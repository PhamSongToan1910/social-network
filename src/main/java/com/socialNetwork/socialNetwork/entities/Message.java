package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("message")
public class Message extends BaseEntity{

    private static final String ID_SENDER = "id_sender";
    private static final String CONTENT = "content";
    private static final String TYPE = "type";
    private static final String ID_GROUP = "id_group";

    @Field(ID_SENDER)
    private String idSender;

    @Field(CONTENT)
    private String content;

    @Field(TYPE)
    private int type; //text, file or image

    @Field(ID_GROUP)
    private String idGroup;
}
