package com.socialNetwork.socialNetwork.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private Boolean gender; //1 is male 0 is female

    private String address;

    private Byte[] avt;

    private String DOB;

    private String playerId;
}
