package com.procs.pcs_.repository;

import com.procs.pcs_.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Integer> {
    @Transactional(readOnly = true)
    Optional<UsersEntity> findByLogin(String login);

    @Transactional(readOnly = true)
    UsersEntity getByLogin(String login);

    boolean existsByLogin(String login);
}
