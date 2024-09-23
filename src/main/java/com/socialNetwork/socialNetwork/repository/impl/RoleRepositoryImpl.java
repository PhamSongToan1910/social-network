package com.socialNetwork.socialNetwork.repository.impl;

import com.socialNetwork.socialNetwork.entities.Role;
import com.socialNetwork.socialNetwork.repository.AbstractRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.RoleRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.ROLE_COLLECTION;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role> implements RoleRepository {

    public RoleRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public void save(Role entity) {
        mongoTemplate.save(entity, ROLE_COLLECTION);
    }
}
