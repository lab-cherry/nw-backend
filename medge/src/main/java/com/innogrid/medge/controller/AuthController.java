package com.innogrid.medge.controller;

import java.util.*;

import com.innogrid.medge.model.RoleEntity;
import com.innogrid.medge.model.UserEntity;
import com.innogrid.medge.model.dto.UserLoginDto;
import com.innogrid.medge.model.dto.UserRegisterDto;
import com.innogrid.medge.service.AuthService;
import com.innogrid.medge.service.UserService;
import com.innogrid.medge.util.Security.AccessToken;
import com.innogrid.medge.util.Security.jwt.IJwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto registerRequest) {

        AccessToken accessToken =  authService.register(registerRequest);

        return new ResponseEntity<>(accessToken, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto loginRequest) {

        AccessToken accessToken =  authService.login(loginRequest);

//        Map<String, Object> map = new LinkedHashMap<>();
//        map.put("message", "Success Created.");
//        map.put("code", "200");
//        map.put("username", loginRequest.getUsername());
//        map.put("token", accessToken.getToken());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken.getToken());

        return new ResponseEntity<>(accessToken, httpHeaders, HttpStatus.OK);

    }

}


