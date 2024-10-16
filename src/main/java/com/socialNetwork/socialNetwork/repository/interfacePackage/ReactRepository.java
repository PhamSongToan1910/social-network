package com.socialNetwork.socialNetwork.repository.interfacePackage;

import com.socialNetwork.socialNetwork.entities.React;
import com.socialNetwork.socialNetwork.repository.Repository;

import java.util.List;
import java.util.Map;

public interface ReactRepository extends Repository<React> {
    React findByUserIdAndPostId(String userId, String postId);
    Map<String, Integer> getReactCounts(List<String> postIds);
}
