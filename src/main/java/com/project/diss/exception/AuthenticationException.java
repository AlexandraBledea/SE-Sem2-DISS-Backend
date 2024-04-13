package com.project.diss.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends BaseException{
    public AuthenticationException() {
        super(ServiceErrorCodes.AUTHENTICATION_ERROR.errorCode(),
                ServiceErrorCodes.AUTHENTICATION_ERROR.errorName(),
                ServiceErrorCodes.AUTHENTICATION_ERROR.errorMessage());
    }
}
