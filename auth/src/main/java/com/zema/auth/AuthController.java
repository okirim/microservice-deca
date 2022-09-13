package com.zema.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import com.zema.commons.BasePath;
@RestController
@RequestMapping(path = BasePath.AUTH_CONTROLLER_PATH,produces={MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthVM login(@Valid @RequestBody AuthDto authDto) {
        var token = authService.login(authDto);
        return new AuthVM(token);
    }


}
