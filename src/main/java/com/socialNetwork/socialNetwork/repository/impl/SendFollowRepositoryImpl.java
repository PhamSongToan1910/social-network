package com.socialNetwork.socialNetwork.repository.impl;

import com.socialNetwork.socialNetwork.entities.SendFollow;
import com.socialNetwork.socialNetwork.repository.AbstractRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.FollowRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.SendFollowRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.SEND_FOLLOW_REQUEST_COLLECTION;

@Repository
public class SendFollowRepositoryImpl extends AbstractRepository<SendFollow> implements SendFollowRepository {
    public SendFollowRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public void save(SendFollow entity) {
        mongoTemplate.save(entity, SEND_FOLLOW_REQUEST_COLLECTION);
    }
}
