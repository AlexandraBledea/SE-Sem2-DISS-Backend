package com.project.diss.util.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority(T(com.project.diss.persistance.entity.enums.UserType).ADMIN)")
@Target(ElementType.METHOD)
public @interface AllowAdmin {

}
