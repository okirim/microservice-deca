package com.zema.app.role.rules;//package com.zema.app.role.rules;

import com.zema.app.role.Role;
import com.zema.app.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniqueDescriptionValidator.class)
public @interface UniqueDescription {

    String message() default "{app.constraints.UniqueDescription.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class UniqueDescriptionValidator implements ConstraintValidator<UniqueDescription, String> {
@Autowired
private RoleRepository roleRepository;


    @Override
    public void initialize(UniqueDescription constraintAnnotation) {
    }

    public boolean isValid(String description, ConstraintValidatorContext cxt) {
        Role role= roleRepository.findByDescription(description);
        return role == null;
    }
}
