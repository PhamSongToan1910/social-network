package com.socialNetwork.socialNetwork.controller;

import com.socialNetwork.socialNetwork.controller.request.AcceptFollowRequest;
import com.socialNetwork.socialNetwork.controller.request.UserRequest;
import com.socialNetwork.socialNetwork.controller.request.UserSendFollowRequest;
import com.socialNetwork.socialNetwork.controller.response.ResponseData;
import com.socialNetwork.socialNetwork.entities.SendFollow;
import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.service.FollowService;
import com.socialNetwork.socialNetwork.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/social-network/user")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;
    FollowService followService;

    @PostMapping("/register")
    public ResponseData<String> register(@RequestBody UserRequest userRequest) throws IOException {
        String userId = userService.createUser(userRequest);
        return new ResponseData<>(200, userId);
    }

    @GetMapping("/test")
    public String test() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            String username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
            System.out.println("Username: " + username);
            return username;
        } else {
            // Trong trường hợp người dùng chưa đăng nhập, `principal` sẽ là chuỗi "anonymousUser"
            String username = principal.toString();
            System.out.println("Username: " + username);
            return username;
        }
    }

    @PostMapping("/send-follow-request-noti")
    public ResponseData<String> sendFollowRequest(@RequestBody UserSendFollowRequest userSendFollowRequest) {
        String id = userService.sendFollowRequset(userSendFollowRequest);
        return new ResponseData<>(200, id);
    }

    @PostMapping("/accept-follow-request-noti")
    public ResponseData<String> acceptFollowRequest(@RequestBody AcceptFollowRequest acceptFollowRequest) {
        String id = followService.acceptFollowRequest(acceptFollowRequest);
        return new ResponseData<>(200, id);
    }
}
