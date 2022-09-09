package com.zema.commons.exceptions;

public enum ErrorMessage {

    MISSING_REQUIRED_FIELD("Missing required field, Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    PAGE_NUMBER_MUST_BE_GREATER_THAN_ZERO("Page number must be greater than 0"),
    PAGE_SIZE_MUST_BE_GREATER_THAN_ZERO("pageSize number must be greater than 0"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    NO_RECORD_FOUND("Record not found"),
    BAD_REQUEST("Bad Request"),
    NOT_FOUND_RECODE( "there is no record for this id"),
    AUTHENTICATION_FAILED("Authentication Failed"),
    COULD_NOT_UPDATE_RECORD("Could not updated record"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    UNMATCHED_PASSWORD_FIELD("Password and confirm password are unmatched"),
    EMAIL_ADDRESS_ALREADY_EXISTS("Email address already exists"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email Address could not be verified"),
    INVALID_URL_PARAMETER("invalid parameters"),
    ACCOUNT_LOCKED("Your account has been locked. Please contact administration"),
    METHOD_IS_NOT_ALLOWED ("This request method is not allowed on this endpoint. Please send a '%s' request"),
    INTERNAL_SERVER_ERROR_MSG( "An error occurred while processing the request"),
    INCORRECT_CREDENTIALS ( "Username / password incorrect. Please try again"),
    ACCOUNT_DISABLED( "Your account has been disabled. If this is an error, please contact administration"),
    ERROR_PROCESSING_FILE ( "Error occurred while processing file"),
    NOT_ENOUGH_PERMISSION( "You do not have enough permission"),
    NOT_FOUND( "there is no map for this Url"),
    ERROR_PATH( "/error");
    final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
