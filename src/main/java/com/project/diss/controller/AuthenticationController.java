package com.project.diss.controller;

import com.project.diss.controller.dto.LoginUser;
import com.project.diss.controller.dto.Token;
import com.project.diss.exception.AuthenticationException;
import com.project.diss.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/login")
@CrossOrigin()
@Slf4j
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity<Token> authentication(@RequestBody LoginUser loginUser) throws AuthenticationException {

        log.info("Start: User login. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<Token> response = ResponseEntity.ok(userService.createJwtForUser(loginUser.getEmail(), loginUser.getPassword()));
        log.info("End: User login. Timestamp: {}", LocalDateTime.now());
        return response;
    }

}
