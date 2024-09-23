package com.socialNetwork.socialNetwork.configuraion.security;

import com.socialNetwork.socialNetwork.entities.Role;
import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.impl.RoleRepositoryImpl;
import com.socialNetwork.socialNetwork.repository.impl.UserRepositoryImpl;
import com.socialNetwork.socialNetwork.repository.interfacePackage.RoleRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DomainUserDetailsService implements UserDetailsService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Authentication {}", username);
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null) {
            return getUserDetails(userEntity);
        }
        throw new UsernameNotFoundException("username: "+ username + " not found!!!");
    }

    private UserDetails getUserDetails(User userEntity) {
        Set<SimpleGrantedAuthority> authorities = userEntity.getAuthorities()
                .stream()
                .map((id) -> {
                    try {
                        return stringToRole(id);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );
    }

    private String stringToRole(String id) throws Exception {
        return roleRepository.findById(id, Role.class).getName();
    }
}
