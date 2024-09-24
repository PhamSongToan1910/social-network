package com.socialNetwork.socialNetwork.service.impl;

import com.socialNetwork.socialNetwork.controller.request.CreateReactRequest;
import com.socialNetwork.socialNetwork.controller.request.DeleteReactRequest;
import com.socialNetwork.socialNetwork.entities.Post;
import com.socialNetwork.socialNetwork.entities.React;
import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.interfacePackage.PostRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.ReactRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.UserRepository;
import com.socialNetwork.socialNetwork.service.NotificationService;
import com.socialNetwork.socialNetwork.service.ReactService;
import com.socialNetwork.socialNetwork.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactServiceImpl implements ReactService {

    private final ReactRepository reactRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final PostRepository postRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void reactPost(CreateReactRequest request) {
        React react = reactRepository.findByUserIdAndPostId(request.getUserId(), request.getPostId());
        if (react == null) {
            react = castCreateReact(request);
            reactRepository.save(react);

            Post post = postRepository.findById(request.getPostId(), Post.class);

            User user = userRepository.findById(request.getUserId(), User.class);

            switch (react.getType()) {
                case Constant.REACT.LIKE:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.LIKE_POST, post.getUserID());
                    break;
                case Constant.REACT.LOVE:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.LOVE_POST, post.getUserID());
                    break;
                case Constant.REACT.CARE:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.CARE_POST, post.getUserID());
                    break;
                case Constant.REACT.WOW:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.WOW_POST, post.getUserID());
                    break;
                case Constant.REACT.HAHA:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.HAHA_POST, post.getUserID());
                    break;
                case Constant.REACT.SAD:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.SAD_POST, post.getUserID());
                    break;
                case Constant.REACT.ANGRY:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.ANGRY_POST, post.getUserID());
                    break;
            }

        } else {
            react = castCreateReact(request);
            reactRepository.save(react);
        }
    }

    @Override
    public void updateReactPost(CreateReactRequest request) {
        React react = reactRepository.findByUserIdAndPostId(request.getUserId(), request.getPostId());
        if (react != null && !react.getIsReacted().get(request.getType())) {
            react.getIsReacted().put(request.getType(), true);
            Query query = new Query();
            query.addCriteria(Criteria.where(React._ID).is(react.getId()));
            Update update = new Update();
            update.set(React.IS_REACTED, react.getIsReacted())
                    .set(React.TYPE, request.getType())
                    .set(react.LAST_MODIFIED_BY, react.getUserId());

            mongoTemplate.updateFirst(query, update, React.class);

            Post post = postRepository.findById(request.getPostId(), Post.class);

            User user = userRepository.findById(request.getUserId(), User.class);

            switch (react.getType()) {
                case Constant.REACT.LIKE:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.LIKE_POST, post.getUserID());
                    break;
                case Constant.REACT.LOVE:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.LOVE_POST, post.getUserID());
                    break;
                case Constant.REACT.CARE:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.CARE_POST, post.getUserID());
                    break;
                case Constant.REACT.WOW:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.WOW_POST, post.getUserID());
                    break;
                case Constant.REACT.HAHA:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.HAHA_POST, post.getUserID());
                    break;
                case Constant.REACT.SAD:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.SAD_POST, post.getUserID());
                    break;
                case Constant.REACT.ANGRY:
                    notificationService.sendMessageToUser(user.getId().toString(), user.getFullName() + " " + Constant.ANGRY_POST, post.getUserID());
                    break;
            }
        }
    }

    @Override
    public void deleteReactPost(DeleteReactRequest request) {
        React react = reactRepository.findByUserIdAndPostId(request.getUserId(), request.getPostId());
        Query query = new Query();
        query.addCriteria(Criteria.where(React._ID).is(react.getId()));
        Update update = new Update();
        update.set(React.IS_DELETE, true)
                .set(React.LAST_MODIFIED_BY, request.getUserId());

        mongoTemplate.updateFirst(query, update, React.class);
    }

    private React castCreateReact(CreateReactRequest request) {
        React react = new React();
        react.setType(request.getType());
        react.setPosition(request.getPosition());
        react.setPostId(request.getPostId());
        react.getIsReacted().put(request.getType(), true);
        react.setCreatedBy(request.getUserId());
        return react;
    }
}
