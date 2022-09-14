package com.zema.commons.reponses.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class AuthVM implements Serializable{
    String accessToken;

    public AuthVM(String accessToken) {
        this.accessToken = accessToken;
    }
}