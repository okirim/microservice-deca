package com.zema.app;

import com.zema.commons.exceptions.AppExceptionHandler;
import com.zema.commons.exceptions.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public class RoleExceptionHandler extends AppExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrors(List.of(exception.getMessage()));
        errorDetails.setStatus(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }


}
