package com.project.diss.controller;

import com.project.diss.controller.dto.CreateUserDto;
import com.project.diss.controller.dto.UserDto;
import com.project.diss.service.UserService;
import com.project.diss.util.annotations.AllowAdmin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin")
@CrossOrigin()
@Slf4j
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @AllowAdmin
    @PostMapping("/create-account")
    public ResponseEntity<UserDto> createAccount(@RequestBody CreateUserDto user) {
        log.info("Start: Create account. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<UserDto> response = ResponseEntity.ok(userService.createUser(user));
        log.info("End: Create account. Timestamp: {}", LocalDateTime.now());
        return response;
    }
}
