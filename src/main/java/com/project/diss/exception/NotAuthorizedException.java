package com.project.diss.exception;

import lombok.Getter;

@Getter
public class NotAuthorizedException extends BaseException {
    public NotAuthorizedException() {
        super(ServiceErrorCodes.UNAUTHORIZED_ERROR.errorCode(),
                ServiceErrorCodes.UNAUTHORIZED_ERROR.errorName(),
                ServiceErrorCodes.UNAUTHORIZED_ERROR.errorMessage());
    }
}
