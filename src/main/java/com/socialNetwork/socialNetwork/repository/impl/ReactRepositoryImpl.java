package com.socialNetwork.socialNetwork.repository.impl;

import com.socialNetwork.socialNetwork.dto.PostReactCount;
import com.socialNetwork.socialNetwork.entities.React;
import com.socialNetwork.socialNetwork.repository.AbstractRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.ReactRepository;
import com.socialNetwork.socialNetwork.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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

    @Override
    public Map<String, Integer> getReactCounts(List<String> postIds) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where(React.POST_ID).in(postIds));
        GroupOperation groupOperation = Aggregation.group(React.POST_ID)
                .sum(ConditionalOperators.when(Criteria.where(React.TYPE).ne(0)).then(1).otherwise(0))
                .as("reactCount");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation);
        AggregationResults<PostReactCount> results = mongoTemplate.aggregate(aggregation, Constant.COLLECTION.REACT_COLLECTION, PostReactCount.class);
        Map<String, Integer> reactCountMap = new HashMap<>();
        System.out.println(results.getMappedResults());
        for (PostReactCount postReactCount : results.getMappedResults()) {
            reactCountMap.put(postReactCount.getPost_id(), postReactCount.getReactCount());
        } 
        return reactCountMap;
    }
}
