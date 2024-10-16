package com.socialNetwork.socialNetwork.service;

import com.socialNetwork.socialNetwork.controller.request.UserRequest;
import com.socialNetwork.socialNetwork.controller.request.UserSendFollowRequest;
import com.socialNetwork.socialNetwork.entities.User;

import java.io.IOException;

public interface UserService {

    String createUser (UserRequest userRequest) throws IOException;

    User updateUser (UserRequest userRequest);

    String sendFollowRequset (UserSendFollowRequest userSendFollowRequest);

    void updateLoginTime(String userName);

    void updateLastLoginTime(String userName);

    void updatePlayerId(String username, String playerId);
}
