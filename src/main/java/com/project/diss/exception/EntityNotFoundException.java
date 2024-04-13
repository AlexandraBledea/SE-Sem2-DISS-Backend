package com.project.diss.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends BaseException {

  public EntityNotFoundException() {
    super(ServiceErrorCodes.ENTITY_NOT_FOUND.errorCode(),
            ServiceErrorCodes.ENTITY_NOT_FOUND.errorName(),
            ServiceErrorCodes.ENTITY_NOT_FOUND.errorMessage());
  }

}
