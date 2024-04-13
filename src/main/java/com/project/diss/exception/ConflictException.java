package com.project.diss.exception;

import lombok.Getter;

@Getter
public class ConflictException extends BaseException {

  public ConflictException() {
    super(ServiceErrorCodes.CONFLICT_DATABASE_ENTRY.errorCode(),
            ServiceErrorCodes.CONFLICT_DATABASE_ENTRY.errorName(),
            ServiceErrorCodes.CONFLICT_DATABASE_ENTRY.errorMessage());
  }

}
