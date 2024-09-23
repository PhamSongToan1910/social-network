package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.ROLE_COLLECTION;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(ROLE_COLLECTION)
public class Role extends BaseEntity{
    public static final String NAME = "name";

    @Field(NAME)
    private String name;
}
