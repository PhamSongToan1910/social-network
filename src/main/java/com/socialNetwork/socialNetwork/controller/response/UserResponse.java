package com.socialNetwork.socialNetwork.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String userName;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private Boolean gender; //1 is male 0 is female

    private String address;

    private Byte[] avt;

    private String DOB;
}
