package com.zema.commons.reponses;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class HttpResponse<T> implements Serializable {

    private HttpStatus status=HttpStatus.OK;
    private String message="success";
    private T data;

    public HttpResponse(T data) {
        this.data = data;
    }

    public HttpResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public HttpResponse(String message,HttpStatus status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}