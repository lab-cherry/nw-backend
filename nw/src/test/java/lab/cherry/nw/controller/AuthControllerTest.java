package lab.cherry.nw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.cherry.nw.model.dto.UserLoginDto;
import lab.cherry.nw.model.dto.UserRegisterDto;
import lab.cherry.nw.service.AuthService;
import lab.cherry.nw.util.Security.AccessToken;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @Transactional
    @DisplayName("[인증] 사용자를 등록합니다.")
    void testRegister() throws Exception {
        // Mock data
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setEmail("test@example.com");
        registerDto.setPassword("test123");

        AccessToken accessToken = new AccessToken("jwt_token");

        // Mock AuthService
        when(authService.register(registerDto)).thenReturn(accessToken);

        // Perform the request
        RequestBuilder requestBuilder = post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerDto));

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Verify the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("jwt_token"));

        verify(authService, times(1)).register(registerDto);
        verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("[인증] 입력한 사용자 정보를 통해 로그인합니다.")
    void testLogin() throws Exception {
        // Mock data
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setUsername("testUser");
        loginDto.setPassword("test123");

        AccessToken accessToken = new AccessToken("jwt_token");

        // Mock AuthService
        when(authService.login(loginDto)).thenReturn(accessToken);

        // Perform the request
        RequestBuilder requestBuilder = post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginDto));

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Verify the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("jwt_token"));

        verify(authService, times(1)).login(loginDto);
        verifyNoMoreInteractions(authService);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
