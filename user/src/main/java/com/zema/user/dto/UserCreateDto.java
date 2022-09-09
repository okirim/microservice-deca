package com.zema.user.dto;

import com.zema.user.rules.PasswordMatch;
import com.zema.user.rules.UniqueEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@PasswordMatch
public class UserCreateDto {
    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @UniqueEmail
    @NotNull
    private String email;
    @NotNull
    @Size(min = 2,max=25)
    private String username;
    @NotNull
    @Size(min = 8,max=25)
    private String password;
    @NotNull
    private String confirmPassword;
}
