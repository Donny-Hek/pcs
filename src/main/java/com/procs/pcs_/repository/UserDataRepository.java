package com.procs.pcs_.repository;

import com.procs.pcs_.model.UserData;
import com.procs.pcs_.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    boolean findByNameAndSurname(String name, String surname);

    UserData findUserDataByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsUserDataByIdAndSurname(int id, String surname);

    UserData findUserDataByIdAndSurname(int id, String surname);
}
