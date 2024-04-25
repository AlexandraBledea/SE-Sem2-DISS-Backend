package com.project.diss.util;

import com.project.diss.dto.SaveEmployeeDocumentDto;
import com.project.diss.dto.SaveTrainingDocumentDto;
import com.project.diss.dto.SaveUserDto;
import com.project.diss.dto.LoginUserDto;
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

    public static boolean validateUserCreation(SaveUserDto saveUserDto) {
        boolean valid = true;
        if (saveUserDto == null) {
            log.error("User object is null.");
            valid = false;
        } else if (!validateEmail(saveUserDto.getEmail())) {
            valid = false;
        } else if (saveUserDto.getFirstname() == null || saveUserDto.getFirstname().isEmpty()) {
            log.error("Firstname is null or empty.");
            valid = false;
        } else if (saveUserDto.getLastname() == null || saveUserDto.getLastname().isEmpty()) {
            log.error("Lastname is null or empty.");
            valid = false;
        } else if (saveUserDto.getPassword() == null || saveUserDto.getPassword().isEmpty()) {
            log.error("Password is null or empty.");
            valid = false;
        } else if (saveUserDto.getRole() == null || saveUserDto.getRole().isEmpty()) {
            log.error("Role is null or empty.");
            valid = false;
        } else if (saveUserDto.getDepartment() == null || saveUserDto.getDepartment().isEmpty()) {
            log.error("Department is null or empty.");
            valid = false;
        } else if (saveUserDto.getLocation() == null || saveUserDto.getLocation().isEmpty()) {
            log.error("Location is null or empty.");
            valid = false;
        } else if (saveUserDto.getLevel() == null || saveUserDto.getLevel() < 0) {
            log.error("Level is null or negative.");
            valid = false;
        } else if (saveUserDto.getPoints() == null || saveUserDto.getPoints() < 0) {
            log.error("Points is null or negative.");
            valid = false;
        } else if (saveUserDto.getType() == null || saveUserDto.getType().name().isEmpty()) {
            log.error("User type is null or empty.");
            valid = false;
        }

        return valid;
    }

    public static boolean validateEmployeeDocumentCreation(SaveEmployeeDocumentDto employeeDocumentDto) {
        //TODO - add validations
        return true;
    }

    public static boolean validateTrainingDocumentCreation(SaveTrainingDocumentDto trainingDocumentDto) {
        //TODO - add validations
        return true;
    }
}
