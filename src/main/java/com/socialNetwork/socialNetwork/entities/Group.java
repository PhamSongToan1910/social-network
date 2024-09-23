package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("group")
public class Group extends BaseEntity{
    private static final String NAME = "name";
    private static final String AVT = "avt";

    @Field(NAME)
    private String name; //Fomat: user1Id-user2Id

    @Field(AVT)
    private String avt; //Fomat: avt_user1-avt_user2
}
