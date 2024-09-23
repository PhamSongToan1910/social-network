package com.socialNetwork.socialNetwork.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVM {
    private String username;
    private String password;
    private String playerId;
}
