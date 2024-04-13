package com.project.diss.util;

import com.project.diss.controller.dto.CreateUserDto;
import com.project.diss.controller.dto.LoginUserDto;
import com.project.diss.persistance.entity.enums.UserType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            log.error("Email is null or empty.");
            return false;
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            log.error("Email '{}' is invalid.", email);
            return false;
        }
        return true;
    }

    public static boolean validateUserLogin(LoginUserDto loginUser) {
        boolean valid = true;
        if (loginUser == null) {
            log.error("User object is null.");
            valid = false;
        } else if (!validateEmail(loginUser.getEmail())) {
            valid = false;
        } else if (loginUser.getPassword() == null || loginUser.getPassword().isEmpty()) {
            log.error("Password is null or empty.");
            valid = false;
        }
        return valid;
    }

    public static boolean validateUserCreation(CreateUserDto createUserDto) {
        boolean valid = true;
        if (createUserDto == null) {
            log.error("User object is null.");
            valid = false;
        } else if (!validateEmail(createUserDto.getEmail())) {
            valid = false;
        } else if (createUserDto.getFirstname() == null || createUserDto.getFirstname().isEmpty()) {
            log.error("Firstname is null or empty.");
            valid = false;
        } else if (createUserDto.getLastname() == null || createUserDto.getLastname().isEmpty()) {
            log.error("Lastname is null or empty.");
            valid = false;
        } else if (createUserDto.getPassword() == null || createUserDto.getPassword().isEmpty()) {
            log.error("Password is null or empty.");
            valid = false;
        } else if (createUserDto.getRole() == null || createUserDto.getRole().isEmpty()) {
            log.error("Role is null or empty.");
            valid = false;
        } else if (createUserDto.getDepartment() == null || createUserDto.getDepartment().isEmpty()) {
            log.error("Department is null or empty.");
            valid = false;
        } else if (createUserDto.getLocation() == null || createUserDto.getLocation().isEmpty()) {
            log.error("Location is null or empty.");
            valid = false;
        } else if (createUserDto.getLevel() == null || createUserDto.getLevel() < 0) {
            log.error("Level is null or negative.");
            valid = false;
        } else if (createUserDto.getPoints() == null || createUserDto.getPoints() < 0) {
            log.error("Points is null or negative.");
            valid = false;
        } else if (createUserDto.getType() == null || createUserDto.getType().name().isEmpty()) {
            log.error("User type is null or empty.");
            valid = false;
        }

        return valid;
    }
}
