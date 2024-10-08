package com.socialNetwork.socialNetwork.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialNetwork.socialNetwork.entities.Notification;
import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.interfacePackage.NotificationRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.UserRepository;
import com.socialNetwork.socialNetwork.service.NotificationService;
import com.socialNetwork.socialNetwork.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendMessageToAllUsers(String userId, String message, List<String> userIdReceiver) {
        List<Notification> notificationList = userIdReceiver.stream()
                .map(id -> sendMessageToUser(userId, message, id))
                .toList();
        saveNotification(notificationList);
    }

    @Override
    public Notification sendMessageToUser(String userIdSender, String message, String userIdReceiver) {
        try {
            User userReceiver = getPlayerIdByUserId(userIdReceiver);
            if (userReceiver == null || userReceiver.getPlayerId() == null) {
                throw new IllegalArgumentException("Không tìm thấy người dùng hoặc playerId");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", Constant.PUSH_NOTIFICATION.REST_API_KEY);

            Map<String, Object> payload = Map.of(
                    "app_id", Constant.PUSH_NOTIFICATION.APP_ID,
                    "include_player_ids", List.of(userReceiver.getPlayerId()),
                    "data", Map.of("foo", "bar"),
                    "contents", Map.of("en", message)
            );

            String jsonPayload = objectMapper.writeValueAsString(payload);
            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

            String response = restTemplate.postForObject("https://onesignal.com/api/v1/notifications", request, String.class);
            System.out.println("OneSignal API Response: " + response);

            return new Notification(userIdSender, userReceiver.getPlayerId(), message, userIdReceiver, Constant.NOTIFICATION_TYPE.FOLLOW_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private User getPlayerIdByUserId(String userId) {
        return userRepository.findById(userId, User.class);
    }

    private void saveNotification(List<Notification> listNotifications) {
        mongoTemplate.insertAll(listNotifications);
    }
}