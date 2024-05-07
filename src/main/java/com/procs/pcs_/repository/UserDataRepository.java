package com.procs.pcs_.repository;

import com.procs.pcs_.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData,Integer> {
    boolean findByNameAndSurname(String name, String surname);
}
