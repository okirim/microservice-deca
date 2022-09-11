package com.zema.commons.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorsList = new ArrayList<>();
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        if (errors.size() > 0) {
            errorsList.addAll(errors);
        } else {
            var error = Objects.requireNonNull(ex.getGlobalError()).getDefaultMessage();
            errorsList.add(error);
        }
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        errorDetails.setErrors(errorsList);
        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        errorDetails.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorDetails.setMessage(ErrorMessage.INTERNAL_SERVER_ERROR.getErrorMessage());
        //errorDetails.setErrors(List.of(ex.getCause().getMessage()));
        errorDetails.setErrors(List.of(ex.getMessage()));

        log.debug("Exception: {}", ex.getMessage());

//        if (environment.equals("dev")) {
//            errorDetails.setErrors(List.of(ex.getMessage()));
//        }
        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<Object> handleServiceException(AppException exception) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrors(List.of(exception.getMessage()));
        errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, exception.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrors(List.of(exception.getMessage()));
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrors(List.of(exception.getMessage()));
        errorDetails.setStatus(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IOException.class)
    ResponseEntity<Object> iOException() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrors(List.of(ErrorMessage.INTERNAL_SERVER_ERROR.getErrorMessage()));
        errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<Object> usernameNotFoundException() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrors(List.of(ErrorMessage.AUTHENTICATION_FAILED.getErrorMessage()));
        errorDetails.setStatus(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(SignatureException.class)
    ResponseEntity<Object> signatureException() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrors(List.of(ErrorMessage.AUTHENTICATION_FAILED.getErrorMessage()));
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(value
            = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrors(List.of(ex.getMessage()));
        errorDetails.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrors(List.of(ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","))));
        errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

}