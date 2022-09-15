package com.zema.app.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role create(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        return roleRepository.save(role);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRole(Long id) {
        return roleRepository.findById(id);
    }

    public Role update(Long roleId, RoleUpdateDto roleDto) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("Role not found");
        }
        Boolean roleExistsByNameOrDescription = existsByNameOrDescription(roleId,roleDto.getName(), roleDto.getDescription());
        if (roleExistsByNameOrDescription) {
            throw new IllegalArgumentException("Role already exists with name or description");
        }
        role.get().setName(roleDto.getName());
        role.get().setDescription(roleDto.getDescription());
        return roleRepository.save(role.get());
    }

    public void delete(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("Role not found");
        }
        roleRepository.delete(role.get());
    }

    public Boolean existsByNameOrDescription(Long roleId, String roleName, String roleDescription) {
        return roleRepository.existsByNameOrDescription(roleId,roleName, roleDescription);
    }
}
