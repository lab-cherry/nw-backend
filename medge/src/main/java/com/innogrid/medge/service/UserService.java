package com.innogrid.medge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.innogrid.medge.model.UserEntity;


@Component
public interface UserService {
    List<UserEntity> getUsers();
//    void createUser(UserEntity user);
    void deleteUser(Long id);
    UserEntity findById(Long id);
    UserEntity findByUserName(String username);
}
