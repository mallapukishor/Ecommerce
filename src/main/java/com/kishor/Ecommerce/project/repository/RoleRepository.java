package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.AppRoles;
import com.kishor.Ecommerce.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

//import java.lang.ScopedValue;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(AppRoles appRoles);
}
