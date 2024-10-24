package com.viendong.BUOI8.repository;



import com.viendong.BUOI8.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
}
