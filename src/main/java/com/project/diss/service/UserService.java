package com.project.diss.service;

import com.project.diss.configuration.JwtTokenService;
import com.project.diss.converters.BadgeConverter;
import com.project.diss.dto.*;
import com.project.diss.converters.UserConverter;
import com.project.diss.exception.AuthenticationException;
import com.project.diss.exception.ConflictException;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.persistance.BadgeRepository;
import com.project.diss.persistance.entity.BadgeEntity;
import com.project.diss.persistance.entity.TrainingDocumentEntity;
import com.project.diss.persistance.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.diss.persistance.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final BadgeRepository badgeRepository;

    private final JwtTokenService jwtTokenService;

    private final UserConverter userConverter;

    private final BadgeConverter badgeConverter;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
                       BadgeRepository badgeRepository, JwtTokenService jwtTokenService, UserConverter userConverter,
                       BadgeConverter badgeConverter) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.jwtTokenService = jwtTokenService;
        this.userConverter = userConverter;
        this.badgeConverter = badgeConverter;
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
        token.setToken(jwtTokenService.createJwtToken(user.getEmail(), user.getType(), user.getId()));
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

    public UserDto getUserInfo(Long id) throws EntityNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()) {
            log.error("Could not find user with id '{}'.", id);
            throw new EntityNotFoundException();
        }
        return userConverter.convertUserEntityToUserDto(user.get());
    }

    public List<BadgeDto> getUserBadges(Long userId) {
        List<BadgeEntity> badges = badgeRepository.findByUserIdAndCompleted(userId);
        if(badges.isEmpty()) {
            log.error("Could not find badge with userId '{}'", userId);
            return new ArrayList<>();
        }
        return badgeConverter.convertBadgeEntitiesToBadgeDtos(badges);
    }

    public List<UserDto> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        if(users.isEmpty()) {
            log.error("Could not find any users.");
            return new ArrayList<>();
        }
        return userConverter.convertUserEntitiesToUserDtos(users);
    }

    public UserDto updateUser(UserDto dto) throws EntityNotFoundException {
        Optional<UserEntity> user = userRepository.findById(dto.getId());
        if(user.isEmpty()) {
            log.error("Could not find user with id '{}'.", dto.getId());
            throw new EntityNotFoundException();
        }
        UserEntity updatedUser = userConverter.convertUserDtoToUserEntity(dto);
        updatedUser.setPassword(user.get().getPassword());
        return userConverter.convertUserEntityToUserDto(userRepository.save(updatedUser));
    }
    public  void nullifyDocumentReference(Long documentId) {
        List<BadgeEntity> badgeEntitiesCompleted = badgeRepository.findByDocumentIdAndCompleted(documentId);
        badgeEntitiesCompleted = badgeEntitiesCompleted.stream()
                .peek(badge -> badge.setDocument(null))
                .toList();
        badgeRepository.saveAll(badgeEntitiesCompleted);

        List<BadgeEntity> badgeEntitiesInProgress = badgeRepository.findByDocumentIdAndInProgress(documentId);
        badgeRepository.deleteAll(badgeEntitiesInProgress);
    }

    public void deleteUser(Long id) throws EntityNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()) {
            log.error("Could not find user with id '{}'.", id);
            throw new EntityNotFoundException();
        }

        List<TrainingDocumentEntity> trainings = user.get().getDocuments().stream()
                .filter(document -> document instanceof TrainingDocumentEntity)
                .map(document -> (TrainingDocumentEntity) document)
                .toList();

        for(TrainingDocumentEntity training: trainings) {
            nullifyDocumentReference(training.getId());
        }

        userRepository.delete(user.get());
    }

    public List<UserDto> searchForUsers(String search) {
        List<UserEntity> userEntities = userRepository.searchForUser(search);
        if (userEntities.isEmpty()) {
            log.error("Could not find any users for the search key '{}'.", search);
            return new ArrayList<>();
        }
        return userConverter.convertUserEntitiesToUserDtos(userEntities);
    }
}
