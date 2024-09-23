package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("group_detail")
public class Group_detail extends BaseEntity{

    private static final String ID_GROUP = "id_group";
    private static final String ID_MEMBER = "id_member";

    @Field(ID_GROUP)
    private String idGroup;

    @Field(ID_MEMBER)
    private String idMember;
}
