package com.socialNetwork.socialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.RESOURCE_COLLECTION;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(RESOURCE_COLLECTION)
public class Resource extends BaseEntity {

    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String DATA = "data";

    @Field(NAME)
    private String name;

    @Field(TYPE)
    private String type;

    @Field(DATA)
    private byte[] data;
}
