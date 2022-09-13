package com.zema.clients.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient("user")
public interface UserClient {
    @RequestMapping(path = "/api/v1/internal/users/{id}", method = RequestMethod.GET)
    public Optional<User> getUserInternally(@PathVariable("id") String id);

    @GetMapping(path = "/api/v1/internal/users/username/{email}")
    public Optional<User> getUserInternallyByEmail(@PathVariable("email") String email);
}
