package com.socialNetwork.socialNetwork.repository.impl;

import com.socialNetwork.socialNetwork.entities.Notification;
import com.socialNetwork.socialNetwork.repository.AbstractRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.NotificationRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.NOTIFICATION_COLLECTION;

@Repository
public class NotificationRepositoryImpl extends AbstractRepository<Notification> implements NotificationRepository {
    public NotificationRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public Notification save(Notification entity) {
        return mongoTemplate.save(entity, NOTIFICATION_COLLECTION);
    }
}
