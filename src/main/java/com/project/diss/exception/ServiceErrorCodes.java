package com.project.diss.exception;

public record ServiceErrorCodes(String errorCode, String errorName, String errorMessage) {

    // 500 Internal Server Error
    public static final ServiceErrorCodes INTERNAL_SERVER_ERROR = new ServiceErrorCodes("DISS-1", "INTERNAL_SERVER_ERROR", "The service encountered an unexpected condition that prevented it from fulfilling the request. Try again later. If the problem persists, report this issue to the service owner.");
    // Thrown when the entity is not found in the database
    public static final ServiceErrorCodes ENTITY_NOT_FOUND = new ServiceErrorCodes("DISS-2", "ENTITY_NOT_FOUND", "Could not find an database entry according to the request.");
    // Thrown when there is a conflict in the database
    public static final ServiceErrorCodes CONFLICT_DATABASE_ENTRY = new ServiceErrorCodes("DISS-3", "CONFLICT", "There is an conflicting entry in the database.");
    // Throw when the user is not authorized to perform the operation because of its type
    public static final ServiceErrorCodes ACCESS_DENIED_ERROR = new ServiceErrorCodes("DISS-4", "FORBIDDEN", "Access is denied. You are not authorized to perform this operation.");
    // Thrown when the token is missing
    public static final ServiceErrorCodes UNAUTHORIZED_ERROR = new ServiceErrorCodes("DISS-5", "UNAUTHORIZED", "Authentication is required. You are not authorized to perform this operation.");
    // Thrown when the authentication fails because of invalid credentials
    public static final ServiceErrorCodes AUTHENTICATION_ERROR = new ServiceErrorCodes("DISS-6", "AUTHENTICATION_FAILED", "Authentication failed. Please check your credentials and try again.");
    // Thrown when the token is invalid
    public static final ServiceErrorCodes JWT_TOKEN_ERROR = new ServiceErrorCodes("DISS-7", "JWT_TOKEN_ERROR", "Unable to process request due to an invalid token. Please retry or contact support if the issue persists.");
    // Thrown when the request is not structured in the expected format or contains invalid fields
    public static final ServiceErrorCodes INVALID_REQUEST = new ServiceErrorCodes("DISS-8", "INVALID_REQUEST", "The request is not structured in the expected format or contains invalid fields.");
}
