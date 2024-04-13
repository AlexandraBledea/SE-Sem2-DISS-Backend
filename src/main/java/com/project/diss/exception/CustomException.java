package com.project.diss.exception;

import lombok.Getter;

@Getter
public class CustomException extends BaseException{
    public CustomException(String errorName, String errorMessage) {
        super("DISS-Custom", errorName, errorMessage);
    }
}
