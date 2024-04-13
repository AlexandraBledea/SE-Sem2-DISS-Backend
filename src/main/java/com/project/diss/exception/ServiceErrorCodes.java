package com.project.diss.exception;

public record ServiceErrorCodes(String errorCode, String errorName, String errorMessage) {

    public static final ServiceErrorCodes INTERNAL_SERVER_ERROR = new ServiceErrorCodes("DISS-1", "INTERNAL_SERVER_ERROR", "The service encountered an unexpected condition that prevented it from fulfilling the request. Try again later. If the problem persists, report this issue to the service owner.");
    public static final ServiceErrorCodes ENTITY_NOT_FOUND = new ServiceErrorCodes("DISS-2", "ENTITY_NOT_FOUND", "Could not find an database entry according to the request.");
    public static final ServiceErrorCodes CONFLICT_DATABASE_ENTRY = new ServiceErrorCodes("DISS-3", "CONFLICT", "There is an conflicting entry in the database.");
    public static final ServiceErrorCodes ACCESS_DENIED_ERROR = new ServiceErrorCodes("DISS-4", "FORBIDDEN", "Access is denied. You are not authorized to perform this operation.");
    public static final ServiceErrorCodes UNAUTHORIZED_ERROR = new ServiceErrorCodes("DISS-5", "UNAUTHORIZED", "Authentication is required. You are not authorized to perform this operation.");
    public static final ServiceErrorCodes AUTHENTICATION_ERROR = new ServiceErrorCodes("DISS-6", "AUTHENTICATION_FAILED", "Authentication failed. Please check your credentials and try again.");

    public static final ServiceErrorCodes JWT_TOKEN_ERROR = new ServiceErrorCodes("DISS-7", "JWT_TOKEN_ERROR", "Unable to process request due to an invalid token. Please retry or contact support if the issue persists.");
}
