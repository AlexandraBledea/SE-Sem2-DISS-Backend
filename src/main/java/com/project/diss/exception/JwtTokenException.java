package com.project.diss.exception;

public class JwtTokenException extends BaseException{
    public JwtTokenException() {
        super(ServiceErrorCodes.JWT_TOKEN_ERROR.errorCode(),
                ServiceErrorCodes.JWT_TOKEN_ERROR.errorName(),
                ServiceErrorCodes.JWT_TOKEN_ERROR.errorMessage());
    }
}
