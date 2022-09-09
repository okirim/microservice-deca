package com.zema.user.rules;

import com.zema.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "{app.constraints.UniqueEmail.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public void initialize(UniqueEmail constraintAnnotation) {
//        constraint
//    }

    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return userRepository.findByEmail(email).isEmpty();
    }
}

