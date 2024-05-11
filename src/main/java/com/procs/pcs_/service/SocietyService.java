package com.procs.pcs_.service;

import com.procs.pcs_.model.SocietyEntity;
import com.procs.pcs_.model.UserData;
import com.procs.pcs_.model.UsersEntity;
import com.procs.pcs_.repository.SocietyRepository;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocietyService {
    private final SocietyRepository societyRepository;
    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;

    public boolean addSociety(String nameSociety, String nameAdmin) {
        UserData admin = userDataRepository.findUserDataByEmail(nameAdmin);
        if (admin.getSociety() == null && !societyRepository.existsByName(nameAdmin)) {
            SocietyEntity society = new SocietyEntity(nameSociety, admin);
            societyRepository.save(society);
            return true;
        } else return false;
    }

    public boolean addUserToSociety(int userid, String email, String nameAdmin) {
        UserData admin = userDataRepository.findUserDataByEmail(nameAdmin);
        if (userDataRepository.existsByEmail(email)
                && userDataRepository.getById(userid) == userDataRepository.findUserDataByEmail(email)) {//существует ли пользователь, которого добавляем
            UserData user = userDataRepository.findUserDataByEmail(email);
            if (user.getSociety() == null) {//состоит ли УЖЕ добавляемый пользователь в группе
                SocietyEntity society = admin.getSociety();
                society.addUserToList(user);
                societyRepository.save(society);
//                user.setSociety(admin.getSociety());
                return true;
            }
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
