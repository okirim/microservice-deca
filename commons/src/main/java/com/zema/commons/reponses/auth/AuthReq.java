package com.zema.commons.reponses.auth;

import lombok.Data;


@Data
public class AuthReq {
    private String email;
    private String password;
}
