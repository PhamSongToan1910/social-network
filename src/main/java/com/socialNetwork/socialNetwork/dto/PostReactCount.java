package com.socialNetwork.socialNetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReactCount {
    @Field("_id")
    private String post_id;
    private int reactCount;
}
