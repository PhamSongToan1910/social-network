package com.socialNetwork.socialNetwork.utils;

import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.interfacePackage.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@Component
@RequiredArgsConstructor
public class Utils {

    static UserRepository userRepository;

    private static final String DateFormat = "yyyyMMddHHmmssSSS";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat);

    public static Long getCurrentTime() {
        return new Date().getTime();
    }

    public static String convertDateToString(Date time) {
        return simpleDateFormat.format(time);
    }

    public static User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userRepository.findByUsername(username);
    }
}
