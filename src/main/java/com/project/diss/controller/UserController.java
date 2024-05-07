package com.project.diss.controller;

import com.project.diss.dto.BadgeDto;
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
import java.util.List;

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

    @GetMapping(value = USER_BASE_URL + "/badges/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BadgeDto>> getUserBadges(@PathVariable("id") Long id) throws EntityNotFoundException {
        log.info("Start: Get user badges. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<List<BadgeDto>> response = ResponseEntity.ok(userService.getUserBadges(id));
        log.info("End: Get user badges. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value = USER_BASE_URL + GET_USERS_SUB_PATH, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getUsers() {
        log.info("Start: Get users. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<List<UserDto>> response = ResponseEntity.ok(userService.getUsers());
        log.info("End: Get users. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @PutMapping(value = USER_BASE_URL + UPDATE_USER_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUserProgress(@RequestBody UserDto user) throws EntityNotFoundException {
        log.info("Start: Update user progress. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<UserDto> response = ResponseEntity.ok(userService.updateUser(user));
        log.info("End: Update user progress. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @DeleteMapping(value = USER_BASE_URL + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) throws EntityNotFoundException {
        log.info("Start: Delete user. Timestamp: {}", LocalDateTime.now());
        userService.deleteUser(id);
        ResponseEntity<Object> response = ResponseEntity.ok().build();
        log.info("End: Delete user. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value = USER_BASE_URL + SEARCH_USERS_SUB_PATH + "/{searchKey}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> searchForUsers(@PathVariable("searchKey") String searchKey) {
        log.info("Start: Search for users. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<List<UserDto>> response = ResponseEntity.ok(userService.searchForUsers(searchKey));
        log.info("End: Search for users. Timestamp: {}", LocalDateTime.now());
        return response;
    }
}
