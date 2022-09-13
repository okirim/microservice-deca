//package com.zema.security;
//
//import com.zema.commons.exceptions.ErrorDetails;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.List;
//
//@ControllerAdvice
//public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(AuthenticationException.class)
//    ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception) {
//        ErrorDetails errorDetails = new ErrorDetails();
//        errorDetails.setErrors(List.of(exception.getMessage()));
//        errorDetails.setStatus(HttpStatus.UNAUTHORIZED);
//        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
//    }
//}
