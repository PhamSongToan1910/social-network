package com.socialNetwork.socialNetwork.repository.interfacePackage;

import com.socialNetwork.socialNetwork.entities.Follow;
import com.socialNetwork.socialNetwork.repository.Repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FollowRepository extends Repository<Follow> {
    List<Follow> getFollowerByUserId(String userId);

    List<Follow> getFollowByUserId(String userId);
}
