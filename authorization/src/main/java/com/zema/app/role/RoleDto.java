package com.zema.app.role;//package com.zema.app.role;

import com.zema.app.role.rules.UniqueDescription;
import com.zema.app.role.rules.UniqueRoleName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RoleDto {

    @UniqueRoleName
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @UniqueDescription
    @NotNull
    @Size(min = 8, max = 255)
    private String description;

}
