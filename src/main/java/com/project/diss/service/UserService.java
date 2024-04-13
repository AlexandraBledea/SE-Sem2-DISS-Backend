package com.project.diss.service;

import com.project.diss.configuration.JwtTokenService;
import com.project.diss.controller.model.Token;
import com.project.diss.exception.AuthenticationException;
import com.project.diss.persistance.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.diss.persistance.UserRepository;


@Slf4j
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtTokenService jwtTokenService;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenService jwtTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
    }

    public Token createJwtForUser(String email, String password) throws AuthenticationException {
        UserEntity user = getUserInformation(email, password);
        Token token = new Token();
        token.setToken(jwtTokenService.createJwtToken(user.getEmail(), user.getType(), user.getId()));
        return token;
    }

    public UserEntity getUserInformation(String email, String password) throws AuthenticationException {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())){
            throw new AuthenticationException();
        }
        return user;
    }

}
