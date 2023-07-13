//package lab.cherry.nw.service.Impl;
//
//import lab.cherry.nw.error.exception.CustomException;
//import lab.cherry.nw.model.RoleEntity;
//import lab.cherry.nw.model.UserEntity;
//import lab.cherry.nw.model.dto.UserLoginDto;
//import lab.cherry.nw.model.dto.UserRegisterDto;
//import lab.cherry.nw.repository.RoleRepository;
//import lab.cherry.nw.repository.UserRepository;
//import lab.cherry.nw.util.Security.AccessToken;
//import lab.cherry.nw.util.Security.jwt.IJwtTokenProvider;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.sql.Timestamp;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AuthServiceImplTest {
//
//    @Mock
//    private IJwtTokenProvider jwtTokenProvider;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @InjectMocks
//    private AuthServiceImpl authService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void testRegister() {
//        // Mock data
//        Integer roleId = 1;
//
//        UserRegisterDto userRegisterDto = new UserRegisterDto();
//        userRegisterDto.setUsername("user1");
//        userRegisterDto.setEmail("user1@example.com");
//        userRegisterDto.setPassword("password");
//
//        RoleEntity roleEntity = new RoleEntity();
//        roleEntity.setId(roleId);
//        roleEntity.setName("ROLE_USER");
//
//        Date date = new Date();
//        UserEntity userEntity = UserEntity.builder()
//                .username(userRegisterDto.getUsername())
//                .email(userRegisterDto.getEmail())
//                .password("encodedPassword")
//                .roles(Collections.singleton(roleEntity))
//                .enabled(true)
//                .created_at(new Timestamp(date.getTime()))
//                .build();
//
//        // Mock UserRepository
//        when(userRepository.existsByUsername(userRegisterDto.getUsername())).thenReturn(false);
//        when(passwordEncoder.encode(userRegisterDto.getPassword())).thenReturn("encodedPassword");
//        when(userRepository.save(userEntity)).thenReturn(userEntity);
//        when(roleRepository.findByName("ROLE_USER")).thenReturn(roleEntity);
//
//        // Mock JwtTokenProvider
//        AccessToken accessToken = new AccessToken("token");
//        when(jwtTokenProvider.createJwtToken(userRegisterDto.getUsername(), userEntity.getRoles())).thenReturn(accessToken);
//
//        // Test the service method
//        AccessToken result = authService.register(userRegisterDto);
//
//        // Verify the result
//        assertEquals(accessToken, result);
//
//        // Verify that the UserRepository and JwtTokenProvider methods were called
//        verify(userRepository, times(1)).existsByUsername(userRegisterDto.getUsername());
//        verify(passwordEncoder, times(1)).encode(userRegisterDto.getPassword());
//        verify(userRepository, times(1)).save(userEntity);
//        verify(roleRepository, times(1)).findByName("ROLE_USER");
//        verify(jwtTokenProvider, times(1)).createJwtToken(userRegisterDto.getUsername(), userEntity.getRoles());
//        verifyNoMoreInteractions(userRepository, passwordEncoder, roleRepository, jwtTokenProvider);
//    }
//
//    @Test
//    void testRegister_WithExistingUsername() {
//        // Mock data
//        UserRegisterDto userRegisterDto = new UserRegisterDto();
//        userRegisterDto.setUsername("user1");
//        userRegisterDto.setRoles(new String[]{"ROLE_USER"});
//
//        // Mock UserRepository
//        when(userRepository.existsByUsername(userRegisterDto.getUsername())).thenReturn(true);
//
////        UserEntity userEntity = new UserEntity();
////        userEntity.setRoles(getRoles(userRegisterDto.getRoles()));
////
////        Set<RoleEntity> roles = userEntity.getRoles();
//
//        // Test the service method and verify the exception
//        assertThrows(CustomException.class, () -> authService.register(userRegisterDto));
//
//        // Verify that the UserRepository method was called
//        verify(userRepository, times(1)).existsByUsername(userRegisterDto.getUsername());
//        verifyNoMoreInteractions(userRepository, passwordEncoder, roleRepository, jwtTokenProvider);
//    }
//
//    @Test
//    void testLogin() {
//        // Mock data
//        Integer userId = 1;
//        UserLoginDto userLoginDto = new UserLoginDto();
//        userLoginDto.setUsername("user1");
//        userLoginDto.setPassword("password");
//
//        RoleEntity roleEntity = new RoleEntity();
//        roleEntity.setId(userId);
//        roleEntity.setName("ROLE_USER");
//
//        UserEntity userEntity = UserEntity.builder()
//                .username(userLoginDto.getUsername())
//                .password("encodedPassword")
//                .roles(Collections.singleton(roleEntity))
//                .build();
//
//        // Mock UserRepository
//        when(userRepository.findByUserName(userLoginDto.getUsername())).thenReturn(Optional.of(userEntity));
//
//        // Mock PasswordEncoder
//        when(passwordEncoder.matches(userLoginDto.getPassword(), userEntity.getPassword())).thenReturn(true);
//
//        // Mock JwtTokenProvider
//        AccessToken accessToken = new AccessToken("token");
//        when(jwtTokenProvider.createJwtToken(userLoginDto.getUsername(), userEntity.getRoles())).thenReturn(accessToken);
//
//        // Test the service method
//        AccessToken result = authService.login(userLoginDto);
//
//        // Verify the result
//        assertEquals(accessToken, result);
//
//        // Verify that the UserRepository, PasswordEncoder, and JwtTokenProvider methods were called
//        verify(userRepository, times(1)).findByUserName(userLoginDto.getUsername());
//        verify(passwordEncoder, times(1)).matches(userLoginDto.getPassword(), userEntity.getPassword());
//        verify(jwtTokenProvider, times(1)).createJwtToken(userLoginDto.getUsername(), userEntity.getRoles());
//        verifyNoMoreInteractions(userRepository, passwordEncoder, roleRepository, jwtTokenProvider);
//    }
//
//    @Test
//    void testLogin_WithInvalidPassword() {
//        // Mock data
//        Integer userId = 1;
//        UserLoginDto userLoginDto = new UserLoginDto();
//        userLoginDto.setUsername("user1");
//        userLoginDto.setPassword("password");
//
//        RoleEntity roleEntity = new RoleEntity();
//        roleEntity.setId(userId);
//        roleEntity.setName("ROLE_USER");
//
//        UserEntity userEntity = UserEntity.builder()
//                .username(userLoginDto.getUsername())
//                .password("encodedPassword")
//                .roles(Collections.singleton(roleEntity))
//                .build();
//
//        // Mock UserRepository
//        when(userRepository.findByUserName(userLoginDto.getUsername())).thenReturn(Optional.of(userEntity));
//
//        // Mock PasswordEncoder
//        when(passwordEncoder.matches(userLoginDto.getPassword(), userEntity.getPassword())).thenReturn(false);
//
//        // Test the service method and verify the exception
//        assertThrows(CustomException.class, () -> authService.login(userLoginDto));
//
//        // Verify that the UserRepository and PasswordEncoder methods were called
//        verify(userRepository, times(1)).findByUserName(userLoginDto.getUsername());
//        verify(passwordEncoder, times(1)).matches(userLoginDto.getPassword(), userEntity.getPassword());
//        verifyNoMoreInteractions(userRepository, passwordEncoder, roleRepository, jwtTokenProvider);
//    }
//
//
//    private Set<RoleEntity> getRoles(String [] roles){
//        Set<RoleEntity> userRoles = new HashSet<>();
//        for(String role : roles) {
//            userRoles.add(roleRepository.findByName(role));
//        }
//        return userRoles;
//    }
//}