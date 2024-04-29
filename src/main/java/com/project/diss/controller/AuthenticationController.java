package com.project.diss.controller;

import com.project.diss.dto.UserLoginDto;
import com.project.diss.dto.Token;
import com.project.diss.exception.AuthenticationException;
import com.project.diss.exception.RequestNotValidException;
import com.project.diss.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.project.diss.util.AppValidator.validateUserLogin;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class AuthenticationController {

    public static final String AUTH_BASE_URL = "/auth";

    public static final String LOGIN_SUB_PATH = "/login";
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = AUTH_BASE_URL + LOGIN_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Token> authentication(@RequestBody UserLoginDto loginUser) throws AuthenticationException, RequestNotValidException {

        ResponseEntity<Token> response;
        log.info("Start: User login. Timestamp: {}", LocalDateTime.now());
        if(!validateUserLogin(loginUser)) {
            log.info("End due to error: User login. Timestamp: {}", LocalDateTime.now());;
            throw new RequestNotValidException();
        } else {
            response = ResponseEntity.ok(userService.createJwtForUser(loginUser.getEmail(), loginUser.getPassword()));
        }
        log.info("End: User login. Timestamp: {}", LocalDateTime.now());
        return response;
    }

}
