package com.zema.auth;


import com.zema.clients.user.UserClient;
import com.zema.commons.exceptions.UsernameNotFoundException;
import com.zema.commons.security.SecurityConstants;
import com.zema.commons.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {
    @Autowired
    private UserClient userClient;
    public String login(AuthDto authDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var user = userClient.getUserInternallyByEmail(authDto.getEmail());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!bCryptPasswordEncoder.matches(authDto.getPassword(), user.get().getPassword())) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        //response token
        return SecurityUtils.generateToken(user.get().getId(), SecurityConstants.EXPIRATION_TIME);
   }

}
