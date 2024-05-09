package com.project.diss.util;

import com.project.diss.dto.*;
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

    public static boolean validateUserLogin(UserLoginDto loginUser) {
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

    public static boolean validateUserCreation(UserSaveDto userSaveDto) {
        boolean valid = true;
        if (userSaveDto == null) {
            log.error("User object is null.");
            valid = false;
        } else if (!validateEmail(userSaveDto.getEmail())) {
            valid = false;
        } else if (userSaveDto.getFirstname() == null || userSaveDto.getFirstname().isEmpty()) {
            log.error("Firstname is null or empty.");
            valid = false;
        } else if (userSaveDto.getLastname() == null || userSaveDto.getLastname().isEmpty()) {
            log.error("Lastname is null or empty.");
            valid = false;
        } else if (userSaveDto.getPassword() == null || userSaveDto.getPassword().isEmpty()) {
            log.error("Password is null or empty.");
            valid = false;
        } else if (userSaveDto.getRole() == null || userSaveDto.getRole().isEmpty()) {
            log.error("Role is null or empty.");
            valid = false;
        } else if (userSaveDto.getDepartment() == null || userSaveDto.getDepartment().isEmpty()) {
            log.error("Department is null or empty.");
            valid = false;
        } else if (userSaveDto.getLocation() == null || userSaveDto.getLocation().isEmpty()) {
            log.error("Location is null or empty.");
            valid = false;
        } else if (userSaveDto.getLevel() == null || userSaveDto.getLevel() < 0) {
            log.error("Level is null or negative.");
            valid = false;
        } else if (userSaveDto.getPoints() == null || userSaveDto.getPoints() < 0) {
            log.error("Points is null or negative.");
            valid = false;
        } else if (userSaveDto.getType() == null || userSaveDto.getType().name().isEmpty()) {
            log.error("User type is null or empty.");
            valid = false;
        }

        return valid;
    }

    public static boolean validateEmployeeDocumentCreation(EmployeeDocumentSaveDto employeeDocumentDto) {
        //TODO - add validations
        return true;
    }

    public static boolean validateTrainingDocumentCreation(TrainingDocumentSaveDto trainingDocumentDto) {
        //TODO - add validations
        return true;
    }

    public static boolean validateCompanyDocumentCreation(CompanyDocumentSaveDto companyDocumentDto) {
        //TODO - add validations
        return true;
    }
    public static boolean validateCommentCreation(CommentSaveDto commentDto) {
        //TODO - add validations
        return true;
    }

}
