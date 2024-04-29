package com.project.diss.service;

import com.project.diss.configuration.JwtTokenService;
import com.project.diss.dto.UserSaveDto;
import com.project.diss.dto.Token;
import com.project.diss.dto.UserDto;
import com.project.diss.converters.UserConverter;
import com.project.diss.exception.AuthenticationException;
import com.project.diss.exception.ConflictException;
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

    private final UserConverter userConverter;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenService jwtTokenService, UserConverter userConverter) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.userConverter = userConverter;
    }

    private String createUserInitials(UserEntity user) {
        return user.getFirstname().charAt(0) + "" + user.getLastname().charAt(0);
    }

    public Token createJwtForUser(String email, String password) throws AuthenticationException {
        UserEntity user = getUserInformation(email);
        if (user == null) {
            log.error("Authentication failed: No user found with email '{}'.", email);
            throw new AuthenticationException();
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("Authentication failed: Password does not match for user '{}'.", email);
            throw new AuthenticationException();
        }

        Token token = new Token();
        token.setToken(jwtTokenService.createJwtToken(user.getEmail(), user.getType(), user.getId(), createUserInitials(user), user.getLevel(), user.getPoints()));
        log.info("JWT token generated successfully for user '{}'.", email);
        return token;
    }


    public UserEntity getUserInformation(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDto createUser(UserSaveDto dto) throws ConflictException {
        UserEntity user = userConverter.convertSaveUserDtoToUserEntity(dto);
        if(getUserInformation(user.getEmail()) != null) {
            log.error("Could not save database entry because user with email '{}' already exists.", user.getEmail());
            throw new ConflictException();
        }
        return userConverter.convertUserEntityToUserDto(userRepository.save(user));
    }

}
