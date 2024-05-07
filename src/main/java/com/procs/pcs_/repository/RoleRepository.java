package com.procs.pcs_.repository;

import com.procs.pcs_.model.ERole;
import com.procs.pcs_.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
    Optional<RoleEntity> findByName (ERole name);
}
