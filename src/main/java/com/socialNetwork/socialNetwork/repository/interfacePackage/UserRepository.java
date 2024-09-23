package com.socialNetwork.socialNetwork.repository.interfacePackage;

import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.Repository;

public interface UserRepository extends Repository<User> {
    User findByUsername(String username);
}
