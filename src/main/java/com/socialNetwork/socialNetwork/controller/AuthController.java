package com.socialNetwork.socialNetwork.controller;

import com.socialNetwork.socialNetwork.configuraion.security.JWTToken;
import com.socialNetwork.socialNetwork.configuraion.security.TokenProvider;
import com.socialNetwork.socialNetwork.controller.request.LoginVM;
import com.socialNetwork.socialNetwork.controller.response.ResponseData;
import com.socialNetwork.socialNetwork.repository.impl.RoleRepositoryImpl;
import com.socialNetwork.socialNetwork.repository.impl.UserRepositoryImpl;
import com.socialNetwork.socialNetwork.service.UserService;
import com.socialNetwork.socialNetwork.service.impl.PostServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social-network/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {
    TokenProvider tokenProvider;
    AuthenticationManagerBuilder authenticationManagerBuilder;
    UserService userService;
    PostServiceImpl postServiceImpl;

    @PostMapping("/login")
    public ResponseData<JWTToken> authorize(@RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVM.getUsername(),
                loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println("authen: " +  authentication.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        postServiceImpl.init(loginVM.getUsername());
        userService.updateLoginTime(loginVM.getUsername());
        userService.updatePlayerId(loginVM.getUsername(), loginVM.getPlayerId());

        return new ResponseData<>(new JWTToken(jwt));
    }

    @PostMapping("/logout")
    public ResponseData<String> logout() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            String username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
            userService.updateLastLoginTime(username);
            SecurityContextHolder.clearContext();
        } else {
            // Trong trường hợp người dùng chưa đăng nhập, `principal` sẽ là chuỗi "anonymousUser"
            String username = principal.toString();
            System.out.println("Username: " + username);
        }
        return new ResponseData<>(200, "Logout success!!!");
    }

}
