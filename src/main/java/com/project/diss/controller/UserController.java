package com.project.diss.controller;

import com.project.diss.dto.UserDto;
import com.project.diss.dto.UserSaveDto;
import com.project.diss.exception.ConflictException;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.exception.RequestNotValidException;
import com.project.diss.service.UserService;
import com.project.diss.util.annotations.AllowAdmin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.project.diss.util.AppValidator.validateUserCreation;
import static com.project.diss.util.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class UserController {

    public static final String USER_BASE_URL = "/user";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @AllowAdmin
    @PostMapping(value = USER_BASE_URL + CREATE_USER_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createAccount(@RequestBody UserSaveDto user) throws ConflictException, RequestNotValidException {
        log.info("Start: Create account. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<UserDto> response;
        if (!validateUserCreation(user)) {
            log.info("End due to error: Create account. Timestamp: {}", LocalDateTime.now());
            throw new RequestNotValidException();
        } else {
            response = ResponseEntity.ok(userService.createUser(user));
        }
        log.info("End: Create account. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value = USER_BASE_URL  + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("id") Long id) throws EntityNotFoundException {
        log.info("Start: Get user info. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<UserDto> response = ResponseEntity.ok(userService.getUserInfo(id));
        log.info("End: Get user info. Timestamp: {}", LocalDateTime.now());
        return response;
    }


}
