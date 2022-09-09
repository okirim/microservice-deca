package com.zema.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class UserVM implements Serializable {
    private String email;

    private String username;

    private Date createdAt;

    private Date updatedAt;

//    private Set<AddressResponse> addresses;
}
