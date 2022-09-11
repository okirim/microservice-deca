package com.zema.clients.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.http.HttpResponse;

@FeignClient("user")
public interface UserClient {
    @GetMapping(path = "/{id}")
    public ResponseEntity<HttpResponse<UserVM>> getUser(@PathVariable("id") String id);
}
