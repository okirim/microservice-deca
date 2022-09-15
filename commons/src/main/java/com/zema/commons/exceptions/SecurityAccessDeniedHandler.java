package com.zema.commons.exceptions;

public class SecurityAccessDeniedHandler extends RuntimeException{
    public SecurityAccessDeniedHandler(String message) {
        super(message);
    }
}
