package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.UserRepository;
import lab.cherry.nw.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("[UserServiceImplTest] 사용자 1, 사용자2를 추가하고 전체 사용자를 조회합니다.")
    void testGetUsers() {
        // Mock data
        UserEntity user1 = new UserEntity();
        user1.setId(1);
        user1.setUsername("user1");

        UserEntity user2 = new UserEntity();
        user2.setId(2);
        user2.setUsername("user2");

        List<UserEntity> userList = Arrays.asList(user1, user2);

        // Mock UserRepository
        when(userRepository.findAll()).thenReturn(userList);

        // Test the service method
        List<UserEntity> result = userService.getUsers();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals(user1, result.get(0));
        assertEquals(user2, result.get(1));

        // Verify that the UserRepository method was called
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    // TODO: 업데이트 필요
    @Test
    @Transactional
    @DisplayName("[UserServiceImplTest] {id}에 해당하는 사용자를 업데이트합니다.")
    void testUpdateUser() {
        // Mock data
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUsername("user1");

        // Mock UserRepository
        when(userRepository.findByUserName(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Test the service method
        assertDoesNotThrow(() -> userService.updateUser(user));

        // Verify that the UserRepository methods were called
        verify(userRepository, times(1)).findByUserName(user.getUsername());
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @Transactional
    @DisplayName("[UserServiceImplTest] {id}에 해당하는 사용자를 삭제합니다.")
    void testDeleteUser() {
        // Mock data
        Integer userId = 1;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("user1");

        // Mock UserRepository
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Test the service method
        assertDoesNotThrow(() -> userService.deleteUser(userId));

        // Verify that the UserRepository methods were called
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("[UserServiceImplTest] {username}에 해당하는 사용자를 조회합니다.")
    void testFindByUserName() {
        // Mock data
        Integer userId = 1;
        String username = "user1";
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername(username);

        // Mock UserRepository
        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));

        // Test the service method
        UserEntity result = userService.findByUserName(username);

        // Verify the result
        assertEquals(user, result);

        // Verify that the UserRepository method was called
        verify(userRepository, times(1)).findByUserName(username);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("[UserServiceImplTest] {id}에 해당하는 사용자를 조회합니다.")
    void testFindById() {
        // Mock data
        Integer userId = 1;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("user1");

        // Mock UserRepository
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Test the service method
        UserEntity result = userService.findById(userId);

        // Verify the result
        assertEquals(user, result);

        // Verify that the UserRepository method was called
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }
}
