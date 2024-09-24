package com.socialNetwork.socialNetwork.repository.impl;

import com.socialNetwork.socialNetwork.entities.Follow;
import com.socialNetwork.socialNetwork.repository.AbstractRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.FollowRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.FOLLOW_COLLECTION;

@Repository
public class FollowRepositoryImpl extends AbstractRepository<Follow> implements FollowRepository {
    public FollowRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public Follow save(Follow entity) {
        return mongoTemplate.save(entity, FOLLOW_COLLECTION);
    }

    @Override
    public List<Follow> getFollowerByUserId(String userId) {
        Query query = new Query(Criteria.where(Follow.USER_ID).is(userId));
        return mongoTemplate.find(query, Follow.class);
    }

    @Override
    public List<Follow> getFollowByUserId(String userId) {
        Query query = new Query(Criteria.where(Follow.USER_ID_FOLLOW).is(userId));
        return mongoTemplate.find(query, Follow.class);
    }
}
