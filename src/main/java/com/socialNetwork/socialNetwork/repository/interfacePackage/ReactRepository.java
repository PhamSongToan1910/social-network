package com.socialNetwork.socialNetwork.repository.interfacePackage;

import com.socialNetwork.socialNetwork.entities.React;
import com.socialNetwork.socialNetwork.repository.Repository;

public interface ReactRepository extends Repository<React> {
    React findByUserIdAndPostId(String userId, String postId);
}
