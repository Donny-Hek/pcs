package com.procs.pcs_.service;

import com.procs.pcs_.model.*;
import com.procs.pcs_.repository.RoleRepository;
import com.procs.pcs_.repository.SocietyRepository;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;
    private final RoleRepository roleRepository;
    private final SocietyService societyService;
    private final SocietyRepository societyRepository;

    public boolean editUsersRole(int id, String surname, Set<String> role) {
        if (userDataRepository.existsUserDataByIdAndSurname(id, surname)) {
            UsersEntity user = userRepository.getById(id);
            user.setRoles(this.getUserRoleSetFromUserStringSet(role));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Modifying(clearAutomatically = true)
    public int getUserSociety(String login) {
        UserData user = userDataRepository.findUserDataByEmail(login);
        if (user.getSociety() != null) return user.getSociety().getId();
        else return 0;
    }

    @Modifying(clearAutomatically = true)
    public List<UserData> userListWithoutAdmins(List<UserData> list) {
        list.removeIf(item -> userRepository.getById(item.getId()).getRoles().contains(ERole.ADMIN));
        return list;
    }

    public Set<RoleEntity> getUserRoleSetFromUserStringSet(Set<String> roles) {
        Set<RoleEntity> list = new HashSet<>();

        roles.forEach(role -> {
            RoleEntity newRole = switch (role) {
                case "admin" -> roleRepository.findByName(ERole.ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                case "prog" -> roleRepository.findByName(ERole.PROG)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                case "manag" -> roleRepository.findByName(ERole.MANAG)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                default -> throw new IllegalStateException("Unexpected value: " + role);
            };
            if (newRole != null) {
                list.add(newRole);
            }
        });
        return list;
    }
//    public boolean editUsersname(int id, String surname, String name) {
//
//        return false;
//    }
}
