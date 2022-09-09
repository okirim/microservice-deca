package com.zema.commons.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
@Data
@NoArgsConstructor
public class ErrorDetails {

    private HttpStatus status=HttpStatus.INTERNAL_SERVER_ERROR;
    private String message="fail";
    private List<String> errors;

}