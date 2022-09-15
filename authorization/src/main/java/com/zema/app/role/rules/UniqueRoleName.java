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
@Constraint(validatedBy = UniqueRoleNameValidator.class)
public @interface UniqueRoleName {
    String message() default "{app.constraints.UniqueRoleName.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


class UniqueRoleNameValidator implements ConstraintValidator<UniqueRoleName, String> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void initialize(UniqueRoleName constraintAnnotation) {
    }

    public boolean isValid(String name, ConstraintValidatorContext cxt) {
        Role role= roleRepository.findByName(name);
        return role == null;
    }
}