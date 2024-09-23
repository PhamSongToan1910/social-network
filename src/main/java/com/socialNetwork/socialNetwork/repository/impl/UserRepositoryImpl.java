package com.socialNetwork.socialNetwork.repository.impl;

import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.AbstractRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.UserRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.USER_COLLECTION;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public User findByUsername(String username) {
        Query query = new Query(Criteria.where(User.USERNAME).is(username));
        return findOne(query, User.class);
    }

    @Override
    public void save(User entity) {
        mongoTemplate.save(entity, USER_COLLECTION);
    }
}
