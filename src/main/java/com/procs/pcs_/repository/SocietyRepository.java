package com.procs.pcs_.repository;

import com.procs.pcs_.model.SocietyEntity;
import com.procs.pcs_.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocietyRepository extends JpaRepository<SocietyEntity, Integer> {
    boolean existsById(Integer id);

    boolean existsByName(String name);

    SocietyEntity getById(Integer id);

//    @Query("select i from SocietyEntity i where i.usersList in :user")
//    Optional<SocietyEntity> findSocietyEntityByUsersListContaining(@Param("user") UsersEntity user);
}
