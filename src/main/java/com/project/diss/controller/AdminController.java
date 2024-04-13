package com.project.diss.controller;

import com.project.diss.controller.dto.CreateUserDto;
import com.project.diss.controller.dto.UserDto;
import com.project.diss.exception.ConflictException;
import com.project.diss.exception.RequestNotValidException;
import com.project.diss.service.UserService;
import com.project.diss.util.annotations.AllowAdmin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.project.diss.util.AppValidator.validateUserCreation;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class AdminController {

    public static final String ADMIN_BASE_URL = "/admin";

    public static final String CREATE_ACCOUNT_SUB_PATH = "/create-account";
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @AllowAdmin
    @PostMapping(value = ADMIN_BASE_URL + CREATE_ACCOUNT_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createAccount(@RequestBody CreateUserDto user) throws ConflictException, RequestNotValidException {
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
}