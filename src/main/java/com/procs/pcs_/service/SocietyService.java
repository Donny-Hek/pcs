package com.procs.pcs_.service;

import com.procs.pcs_.model.RoleEntity;
import com.procs.pcs_.model.SocietyEntity;
import com.procs.pcs_.model.UserData;
import com.procs.pcs_.repository.SocietyRepository;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.repository.UserRepository;
import com.procs.pcs_.request_response.UserList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SocietyService {
    private final SocietyRepository societyRepository;
    private final UserDataRepository userDataRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public List<UserList> getUserListBySocietyId(int id) {
        List<UserData> userdata = societyRepository.getById(id).getUsersList();
        List<UserList> userList = new ArrayList<>();

        for (UserData elem : userdata) {
            Set<RoleEntity> roles = userRepository.getById(elem.getId()).getRoles();
            Set<String> listRoles = new HashSet<>();
            roles.forEach(item -> listRoles.add(String.valueOf(item.getName())));

            userList.add(new UserList(elem.getId(),
                    elem.getName().charAt(0),
                    elem.getSurname(),
                    listRoles
            ));
        }
        return userList;
    }

    public boolean addSociety(String nameSociety, String nameAdmin) {
        UserData admin = userDataRepository.findUserDataByEmail(nameAdmin);
        if (admin.getSociety() == null && !societyRepository.existsByName(nameAdmin)) {
//            если админ еще не состоит в групп, то он может создать новую
            SocietyEntity society = new SocietyEntity(nameSociety, admin);
            societyRepository.save(society);
            admin.setSociety(society);
            userDataRepository.save(admin);
            return true;
        } else return false;
    }

    @Transactional
    public boolean addUserToSociety(int userid, String email, String nameAdmin) {
        UserData admin = userDataRepository.findUserDataByEmail(nameAdmin);
        if (userDataRepository.existsByEmail(email)
                && userDataRepository.getById(userid) == userDataRepository.findUserDataByEmail(email)) {
            //существует ли пользователь, которого добавляем
            UserData user = userDataRepository.findUserDataByEmail(email);
            if (user.getSociety() == null) {
                //состоит ли УЖЕ пользователь в другой группе
                SocietyEntity society = admin.getSociety();
                society.addUserToList(user);
                societyRepository.save(society);
                user.setSociety(admin.getSociety());
                userDataRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean checkBelongingToSociety(String nameAdmin, int id, String surname) {
        UserData admin = userDataRepository.findUserDataByEmail(nameAdmin);
        if (userDataRepository.existsUserDataByIdAndSurname(id, surname)) {
            UserData user = userDataRepository.findUserDataByIdAndSurname(id, surname);
            return user.getSociety() == admin.getSociety();
        }
        return false;
    }

//    public List<UsersEntity> findSocietyByUserAndGetList(UsersEntity user) {
//        SocietyEntity society = societyRepository.findSocietyEntityByUsersListContaining(user)
//                .orElse(null);
////        int societyid = societyRepository.findSocietyByUserId(userid);
//        if (society != null) {
////            SocietyEntity society = societyRepository.getById(societyid);
//            return society.getUsersList();
//        } else return null;
//    }
}
