package com.socialNetwork.socialNetwork.service;

import com.socialNetwork.socialNetwork.controller.request.CreateReactRequest;
import com.socialNetwork.socialNetwork.controller.request.DeleteReactRequest;

public interface ReactService {

    void reactPost(CreateReactRequest request);
    void updateReactPost(CreateReactRequest request);
    void deleteReactPost(DeleteReactRequest request);
}
