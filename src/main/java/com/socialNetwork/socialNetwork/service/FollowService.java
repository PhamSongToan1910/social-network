package com.socialNetwork.socialNetwork.service;

import com.socialNetwork.socialNetwork.controller.request.AcceptFollowRequest;
import com.socialNetwork.socialNetwork.entities.SendFollow;

import java.util.List;

public interface FollowService {
    String acceptFollowRequest(AcceptFollowRequest acceptFollowRequest);

    List<String> getFollowerByUserId(String userId);
}
