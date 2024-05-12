package com.procs.pcs_.service;

import com.procs.pcs_.model.RoleEntity;
import com.procs.pcs_.model.UserData;
import com.procs.pcs_.model.UsersEntity;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;

    public boolean editUsersRole(int id, String surname, Set<RoleEntity> role) {
        if (userDataRepository.existsUserDataByIdAndSurname(id, surname)) {
            UsersEntity user = userRepository.getById(id);
            user.setRoles(role);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public int getUserSociety(String login) {
        UserData user = userDataRepository.findUserDataByEmail(login);
        if (user.getSociety() != null) return user.getSociety().getId();
        else return 0;
    }

//    public boolean editUsersname(int id, String surname, String name) {
//
//        return false;
//    }
}
