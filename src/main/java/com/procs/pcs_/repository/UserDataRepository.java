package com.procs.pcs_.repository;

import com.procs.pcs_.model.UserData;
import com.procs.pcs_.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    @Transactional(readOnly = true)
    UserData findUserDataByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsUserDataByIdAndSurname(int id, String surname);

    @Transactional(readOnly = true)
    UserData findUserDataByIdAndSurname(int id, String surname);
}
