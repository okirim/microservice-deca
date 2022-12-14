package com.zema.auth;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AuthVM implements Serializable {
    String accessToken;

    public AuthVM(String accessToken) {
        this.accessToken = accessToken;
    }
}
