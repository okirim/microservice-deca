package com.zema.commons;

import org.springframework.http.HttpStatus;

public class AppConfig {
    public static final String TIMEZONE = "Africa/Algiers";
    public static final String RESPONSE_DATE_TIME_FORMAT = "MM-dd-yyyy hh:mm:ss";
    public static final HttpStatus RESPONSE_DEFAULT_HTTP_STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR;
    public static final String RESPONSE_DEFAULT_REASON = "Internal Server Error";
    public static final String RESPONSE_DEFAULT_MESSAGE = "fail";
}