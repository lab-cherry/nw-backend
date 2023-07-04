package lab.cherry.nw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("[UserControllerTest] 사용자 1, 사용자2를 추가하고 전체 사용자를 조회합니다.")
    void testFindAllUsers() throws Exception {
        // Mock data
        UserEntity user1 = new UserEntity();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        UserEntity user2 = new UserEntity();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");

        List<UserEntity> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        // Mock UserService
        when(userService.getUsers()).thenReturn(userList);

        // Perform the request
        RequestBuilder requestBuilder = get("/api/v1/user");

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Verify the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].userName").value("user1"))
                .andExpect(jsonPath("$[0].userEmail").value("user1@example.com"))
                .andExpect(jsonPath("$[1].userId").value(2))
                .andExpect(jsonPath("$[1].userName").value("user2"))
                .andExpect(jsonPath("$[1].userEmail").value("user2@example.com"));

        verify(userService, times(1)).getUsers();
        verifyNoMoreInteractions(userService);
    }

    // TODO: 업데이트 필요
//    @Test
//    @Transactional
//    @DisplayName("[UserControllerTest] {id}에 해당하는 사용자를 업데이트합니다.")
//    void testUpdateUserById() throws Exception {
//        // Mock data
//        UserEntity user = new UserEntity();
//        user.setId(1);
//        user.setUsername("user1");
//        user.setEmail("updated@example.com");
//
//        // Perform the request
//        RequestBuilder requestBuilder = patch("/api/v1/user/{id}", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(user));
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.userId").value(1))
//                .andExpect(jsonPath("$.userName").value("user1"))
//                .andExpect(jsonPath("$.userEmail").value("updated@example.com"));
//
//        verify(userService, times(1)).updateUser(user);
//        verifyNoMoreInteractions(userService);
//    }

    @Test
    @DisplayName("[UserControllerTest] {id}에 해당하는 사용자를 조회합니다.")
    void testFindByUserId() throws Exception {
        // Mock data
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUsername("user1");
        user.setEmail("updated@example.com");

        // Mock UserService
        when(userService.findById(1)).thenReturn(user);

        // Perform the request
        RequestBuilder requestBuilder = get("/api/v1/user/{id}", 1);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Verify the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.userName").value("user1"))
                .andExpect(jsonPath("$.userEmail").value("updated@example.com"));

        verify(userService, times(1)).findById(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @Transactional
    @DisplayName("[UserControllerTest] {id}에 해당하는 사용자를 삭제합니다.")
    void testDeleteUser() throws Exception {
        // Mock data
        Integer userId = 1;

        // Perform the request
        RequestBuilder requestBuilder = delete("/api/v1/user/{id}", userId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Success"));

        verify(userService, times(1)).deleteUser(userId);
        verifyNoMoreInteractions(userService);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
