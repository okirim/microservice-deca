package com.zema.clients.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;




@Data
@NoArgsConstructor
//@PasswordMatch
public class User implements Serializable {

    private Long id;

    private String username;

    private String email;

    private Boolean emailVerificationStatus;

    private String emailVerificationToken;

    private String password;

    private String confirmPassword;

    private String passwordResetToken;

    private Boolean passwordResetStatus;

    private Date createdAt;

    private Date updatedAt;

}