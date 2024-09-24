package com.socialNetwork.socialNetwork.service.impl;

import com.socialNetwork.socialNetwork.controller.request.AcceptFollowRequest;
import com.socialNetwork.socialNetwork.entities.Follow;
import com.socialNetwork.socialNetwork.entities.SendFollow;
import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.interfacePackage.FollowRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.SendFollowRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.UserRepository;
import com.socialNetwork.socialNetwork.service.FollowService;
import com.socialNetwork.socialNetwork.service.NotificationService;
import com.socialNetwork.socialNetwork.utils.Constant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.socialNetwork.socialNetwork.utils.Constant.ACCPET_ADD_FRIEND_REQUEST_MESSAGE;
import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.FOLLOW_COLLECTION;
import static com.socialNetwork.socialNetwork.utils.Constant.COLLECTION.SEND_FOLLOW_REQUEST_COLLECTION;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FollowServiceImpl implements FollowService {

    private final SendFollowRepository sendFollowRepository;
    private final FollowRepository followRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Override
    public String acceptFollowRequest(AcceptFollowRequest acceptFollowRequest) {
        SendFollow beforeSendFollow = sendFollowRepository.findById(acceptFollowRequest.getId(), SendFollow.class);
        beforeSendFollow.setIsAccept(beforeSendFollow.getIsAccept());
        sendFollowRepository.save(beforeSendFollow);

        Follow follow = new Follow(beforeSendFollow.getUserSend(), beforeSendFollow.getUserReceiver());
        followRepository.save(follow);

        User user = userRepository.findById(beforeSendFollow.getUserReceiver(), User.class);
        String name = user.getFirstName() + " " + user.getLastName();
        notificationService.sendMessageToAllUsers(beforeSendFollow.getUserReceiver(), name + ACCPET_ADD_FRIEND_REQUEST_MESSAGE, Arrays.asList(beforeSendFollow.getUserSend()));

        //TODO CHECK LIST FOLLOWER OF USER_SEND -> IF USER_RECEIVER IN -> CREATE CONNECTION CHAT

        return beforeSendFollow.getId().toString();
    }

    @Override
    public List<String> getFollowerByUserId(String userId) {
        List<Follow> followList = followRepository.getFollowerByUserId(userId);
        return followList.stream().map(Follow::getUserIdFollow).collect(Collectors.toList());
    }
}
