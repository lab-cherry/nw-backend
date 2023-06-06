package lab.cherry.nw.service;

import java.util.List;

import org.springframework.stereotype.Component;

import lab.cherry.nw.model.UserEntity;


@Component
public interface UserService {
    List<UserEntity> getUsers();
//    void createUser(UserEntity user);
    void deleteUser(Long id);
    UserEntity findById(Long id);
    UserEntity findByUserName(String username);
}
