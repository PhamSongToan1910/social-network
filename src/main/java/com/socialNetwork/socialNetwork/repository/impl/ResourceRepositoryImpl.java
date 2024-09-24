package com.socialNetwork.socialNetwork.repository.impl;

import com.socialNetwork.socialNetwork.entities.Resource;
import com.socialNetwork.socialNetwork.repository.AbstractRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.ResourceRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.RESOURCE_COLLECTION;

public class ResourceRepositoryImpl extends AbstractRepository<Resource> implements ResourceRepository {
    public ResourceRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public Resource save(Resource entity) {
        return mongoTemplate.save(entity, RESOURCE_COLLECTION);
    }
}
