package com.socialNetwork.socialNetwork.repository.impl;

import com.socialNetwork.socialNetwork.dto.PostCommentCount;
import com.socialNetwork.socialNetwork.dto.PostReactCount;
import com.socialNetwork.socialNetwork.entities.Comment;
import com.socialNetwork.socialNetwork.entities.React;
import com.socialNetwork.socialNetwork.repository.AbstractRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.CommentRepository;
import com.socialNetwork.socialNetwork.utils.Constant;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentRepositoryImpl extends AbstractRepository<Comment> implements CommentRepository {
    public CommentRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public Comment save(Comment entity) {
        return mongoTemplate.save(entity);
    }

    @Override
    public Map<String, Integer> getNumberComments(List<String> postIds) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where(Comment.POST_ID).in(postIds));
        GroupOperation groupOperation = Aggregation.group(Comment.POST_ID)
                .count()
                .as("commentCount");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation);
        AggregationResults<PostCommentCount> results = mongoTemplate.aggregate(aggregation, Constant.COLLECTION.COMMENT_COLLECTION, PostCommentCount.class);
        Map<String, Integer> commentCountMap = new HashMap<>();
        System.out.println(results.getMappedResults());
        for (PostCommentCount postCommentCount : results.getMappedResults()) {
            commentCountMap.put(postCommentCount.getPostId(), postCommentCount.getCommentCount());
        }
        return commentCountMap;
    }

    @Override
    public Map<String, Comment> getCommentsByPostId(String postId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Comment.ID_PARENT).size(0));
        List<Comment> commentList = mongoTemplate.find(query, Comment.class);
        Map<String, Comment> commentMap = new HashMap<>();
        commentList.stream().map(comment -> commentMap.put(comment.getId().toString(), comment));
        return commentMap;
    }
}
