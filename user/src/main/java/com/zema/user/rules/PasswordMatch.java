package com.zema.user.rules;

import com.zema.user.dto.UserCreateDto;

import javax.validation.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {
    //error message
    public String message() default "{app.constraints.MatchPassword.message}";

    //represents group of constraints
    public Class<?>[] groups() default {};

    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
}


class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserCreateDto> {

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserCreateDto user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword().equals(user.getConfirmPassword());
    }

}


