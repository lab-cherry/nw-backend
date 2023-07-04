package lab.cherry.nw.service;

import java.util.List;

import org.springframework.stereotype.Component;

import lab.cherry.nw.model.UserEntity;


@Component
public interface UserService {
    List<UserEntity> getUsers();
    void updateUser(UserEntity user);
    void deleteUser(Integer id);
    UserEntity findById(Integer id);
    UserEntity findByUserName(String username);
}
