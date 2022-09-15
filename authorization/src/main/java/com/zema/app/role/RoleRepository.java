package com.zema.app.role;

import com.zema.app.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role findByDescription(String description);

    @Query("SELECT Count(r) FROM Role r WHERE r.id != ?1 AND (r.name = ?2 OR r.description = ?3)")
    Boolean existsByNameOrDescription(Long id,String roleName, String roleDescription);
}
