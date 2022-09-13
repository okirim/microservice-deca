package com.zema.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthVM {
    String accessToken;

    public AuthVM(String accessToken) {
        this.accessToken = accessToken;
    }
}
