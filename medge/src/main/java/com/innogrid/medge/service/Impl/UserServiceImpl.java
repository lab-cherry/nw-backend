package com.innogrid.medge.service.Impl;

import com.innogrid.medge.error.enums.ErrorCode;
import com.innogrid.medge.error.exception.CustomException;
import com.innogrid.medge.error.exception.EntityNotFoundException;
import com.innogrid.medge.model.UserEntity;
import com.innogrid.medge.repository.UserRepository;
import com.innogrid.medge.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service("userServiceImpl")
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> getUsers() {
        return EntityNotFoundException.requireNotEmpty(userRepository.findAll(), "Users Not Found");
//        List<UserEntity> userEntities = userRepository.findAll();

//        if(!userEntities.isEmpty()) {
//            return userRepository.findAll();
//        } else {
//            throw new EntityNotFoundException("Users Not Found");
//        }
    }

//    @Override
//    public void createUser(UserEntity user) {
//        Date date = new Date();
//
//        log.error("user data : \n, username {}\n, email {}\n, password {}\n, created {}", user.getUsername(), user.getEmail(), user.getPassword(), user.getCreated_at());
//        if (userRepository.findByUserName(user.getUsername()).orElse(null) != null) {
//            throw new CustomException(ErrorCode.DUPLICATE);
//        }
//
//        UserEntity userEntity = UserEntity.builder()
//                        .username(user.getUsername())
//                        .email(user.getEmail())
//                        .password(passwordEncoder.encode(user.getPassword()))
//                        .enabled(true)
//                        .created_at(new Timestamp(date.getTime()))
//                        .build();
//
//        log.error("sdnfsnlf?");
//
////        userRepository.save(new UserEntity(0L, user.getUsername(), user.getPassword(), false, new Timestamp(date.getTime()), user.getRole()));
//        userRepository.save(userEntity);
//    }

    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with Id " + id + " Not Found."));
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findByUserName(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new EntityNotFoundException("User with Name " + username + " Not Found."));
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with Id " + id + " Not Found."));
    }
}
