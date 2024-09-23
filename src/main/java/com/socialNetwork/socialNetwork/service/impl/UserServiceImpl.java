package com.socialNetwork.socialNetwork.service.impl;

import com.socialNetwork.socialNetwork.controller.request.UserRequest;
import com.socialNetwork.socialNetwork.controller.request.UserSendFollowRequest;
import com.socialNetwork.socialNetwork.entities.Role;
import com.socialNetwork.socialNetwork.entities.SendFollow;
import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.interfacePackage.RoleRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.SendFollowRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.UserRepository;
import com.socialNetwork.socialNetwork.service.NotificationService;
import com.socialNetwork.socialNetwork.service.UserService;
import com.socialNetwork.socialNetwork.utils.Constant;
import com.socialNetwork.socialNetwork.utils.ModelMapperUtils;
import com.socialNetwork.socialNetwork.utils.Utils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    SendFollowRepository sendFollowRepository;
    MongoTemplate mongoTemplate;
    ModelMapperUtils modelMapperUtils;
    NotificationService notificationService;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Override
    public String createUser(UserRequest userRequest) {
        System.out.println("userRequest: " + userRequest);
        User user = ModelMapperUtils.toObject(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Query query = new Query(Criteria.where(Role.NAME).is("user"));
        Role role = roleRepository.findOne(query, Role.class);
        user.getAuthorities().add(role.getId().toString());
        return userRepository.insert(user);
    }

    @Override
    public User updateUser(UserRequest userRequest) {
        User userAfter = ModelMapperUtils.toObject(userRequest, User.class);
        Query query = new Query(Criteria.where(User.USERNAME).is(userAfter.getUsername()));
        Update update = new Update().set(User.FIRSTNAME, userAfter.getFirstName())
                .set(User.LASTNAME, userAfter.getLastName())
                .set(User.ADDRESS, userAfter.getAddress())
                .set(User.DAYOFBIRTH, userAfter.getDOB())
                .set(User.GENDER, userAfter.getGender());
        mongoTemplate.updateFirst(query, update, User.class);
        return userAfter;
    }

    @Override
    public String sendFollowRequset(UserSendFollowRequest userSendFollowRequest) {
        SendFollow sendFollow = new SendFollow();
        sendFollow.setUserSend(userSendFollowRequest.getUserIdSender());
        sendFollow.setUserReceiver(userSendFollowRequest.getUserIdReceiver());
        sendFollow.setIsAccept(Constant.SEND_REQUEST.PENDING);
        String idSendRequest = sendFollowRepository.insert(sendFollow);

        if(idSendRequest != null){
            User receiver = userRepository.findById(userSendFollowRequest.getUserIdReceiver(), User.class);
            User sender = userRepository.findById(userSendFollowRequest.getUserIdSender(), User.class);
            Long loginTime = Long.parseLong(receiver.getLoginTime());
            Long lastLoginTime = Long.parseLong(receiver.getLastLoginTime());

            if(loginTime > lastLoginTime) {
                notificationService.sendMessageToAllUsers(sender.getId().toString(), sender.getFirstName() + " " + sender.getLastName() + Constant.ADD_FRIEND_REQUEST_MESSAGE, Arrays.asList(receiver.getId().toString()));
            }
        }

        return idSendRequest;
    }

    @Override
    public void updateLoginTime(String userName) {
        Query query = new Query(Criteria.where(User.USERNAME).is(userName));
        Update update = new Update().set(User.LOGIN_TIME, Utils.convertDateToString(new Date()));
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updateLastLoginTime(String userName) {
        Query query = new Query(Criteria.where(User.USERNAME).is(userName));
        Update update = new Update().set(User.LAST_LOGIN_TIME, Utils.convertDateToString(new Date()));
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updatePlayerId(String username, String playerId) {
        Query query = new Query(Criteria.where(User.USERNAME).is(username));
        Update update = new Update().set(User.PLAYER_ID, playerId);
        mongoTemplate.updateFirst(query, update, User.class);
    }
}
