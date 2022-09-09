package com.zema.commons.reponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponseWithPagination<T> implements Serializable {

    private HttpStatus status=HttpStatus.OK;
    private String message="success";
    private T data;
    private int currentPage;
    private Long totalElements;
    private int totalPages;


//    public HttpResponseWithPagination(T data, int page, Long totalElements,int totalPages) {
//        this.data = data;
//        this.currentPage = page;
//        this.totalElements = totalElements;
//    }
//
//    public HttpResponseWithPagination(HttpStatus status, String message, T data, int page, Long totalElements,int totalPages) {
//        this.status = status;
//        this.message = message;
//        this.data = data;
//        this.currentPage = page;
//        this.totalElements = totalElements;
//    }
}