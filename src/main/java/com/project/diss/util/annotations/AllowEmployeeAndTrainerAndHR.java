package com.project.diss.util.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority(T(com.project.diss.persistance.entity.enums.UserType).EMPLOYEE)" +
        "or hasAuthority(T(com.project.diss.persistance.entity.enums.UserType).TRAINER)" +
        "or hasAuthority(T(com.project.diss.persistance.entity.enums.UserType).HR)")
@Target(ElementType.METHOD)
public @interface AllowEmployeeAndTrainerAndHR {
}
