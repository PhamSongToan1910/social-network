package com.socialNetwork.socialNetwork.repository;

import com.socialNetwork.socialNetwork.entities.BaseEntity;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Repository<E extends BaseEntity> {
    String insert(E entity);
    List<E> findAll(Class<E> type);
    E findOne(Query query, Class<E> type);
    E findById(String id, Class<E> type);
    List<E> findByIds(List<String> ids, Class<E> type);
    List<E> findByIds(Set<String> ids, Class<E> type);
    E save (E entity);
}
