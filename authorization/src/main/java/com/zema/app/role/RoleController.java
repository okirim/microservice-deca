package com.zema.app.role;

import com.zema.commons.BasePath;
import com.zema.commons.reponses.HttpResponse;
import com.zema.commons.reponses.roles.RoleVM;
import com.zema.commons.validations.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    ModelMapper modelMapper;

    RoleController(){
        modelMapper=new ModelMapper();
    }

    @GetMapping(BasePath.ROLE_CONTROLLER_PATH)
    public ResponseEntity<HttpResponse<List<RoleVM>>> getRoles() {
        List<Role> roles = roleService.getRoles();
        List<RoleVM> rolesVM = new ArrayList<>();
        roles.forEach(role -> {
            rolesVM.add(modelMapper.map(role, RoleVM.class));
        });
        return ResponseEntity.ok(new HttpResponse<>(rolesVM));
    }

    @GetMapping(BasePath.ROLE_CONTROLLER_PATH + "/{id}")
    public ResponseEntity<HttpResponse<RoleVM>> getRole(@PathVariable String id) {
        var isValidId = ValidationUtils.validId(id);
        if (!isValidId) {
            throw new IllegalArgumentException("Invalid id");
        }
        Long roleId = Long.parseLong(id);
        Optional<Role> role = roleService.getRole(roleId);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("Role not found");
        }
        RoleVM roleVM = modelMapper.map(role.get(), RoleVM.class);
        return ResponseEntity.ok(new HttpResponse<>(roleVM));
    }

    @PostMapping(BasePath.ROLE_CONTROLLER_PATH)
    public ResponseEntity<HttpResponse<RoleVM>> create(@Valid @RequestBody RoleDto roleDto) {
        Role role = roleService.create(roleDto);
        RoleVM roleVM = modelMapper.map(role, RoleVM.class);
        return ResponseEntity.ok(new HttpResponse<>(roleVM));
    }

    @PatchMapping(path = BasePath.ROLE_CONTROLLER_PATH + "/{id}")
    public ResponseEntity<HttpResponse<RoleVM>> update(@PathVariable String id, @Valid @RequestBody RoleUpdateDto roleDto) {
        var isValidId = ValidationUtils.validId(id);
        if (!isValidId) {
            throw new IllegalArgumentException("Invalid id");
        }
        Long roleId = Long.parseLong(id);
        Role role = roleService.update(roleId, roleDto);
        RoleVM roleVM = modelMapper.map(role, RoleVM.class);
        return ResponseEntity.ok(new HttpResponse<>(roleVM));
    }

    @DeleteMapping(path = BasePath.ROLE_CONTROLLER_PATH + "/{id}")
    public ResponseEntity<HttpResponse<RoleVM>> delete(@PathVariable String id) {
        var isValidId = ValidationUtils.validId(id);
        if (!isValidId) {
            throw new IllegalArgumentException("Invalid id");
        }
        Long roleId = Long.parseLong(id);
        roleService.delete(roleId);
        return ResponseEntity.ok(new HttpResponse<>());
    }


}
