package com.socialNetwork.socialNetwork.repository.impl;

import com.socialNetwork.socialNetwork.entities.React;
import com.socialNetwork.socialNetwork.repository.AbstractRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.ReactRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ReactRepositoryImpl extends AbstractRepository<React> implements ReactRepository {
    public ReactRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public React save(React entity) {
        return mongoTemplate.save(entity);
    }

    @Override
    public React findByUserIdAndPostId(String userId, String postId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(React.USER_ID).is(userId));
        query.addCriteria(Criteria.where(React.POST_ID).is(postId));
        return mongoTemplate.findOne(query, React.class);
    }
}
