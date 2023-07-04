package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.UserRepository;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    }

    public void updateUser(UserEntity user) {
        findByUserName(user.getUsername());
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with Id " + id + " Not Found."));
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserEntity findByUserName(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new EntityNotFoundException("User with Name " + username + " Not Found."));
    }

    @Transactional(readOnly = true)
    public UserEntity findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with Id " + id + " Not Found."));
    }
}
