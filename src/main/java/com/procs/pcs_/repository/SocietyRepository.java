package com.procs.pcs_.repository;

import com.procs.pcs_.model.SocietyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocietyRepository extends JpaRepository<SocietyEntity, Integer> {
    boolean existsByName(String name);

    Optional<SocietyEntity> findByName(String integer);
}
