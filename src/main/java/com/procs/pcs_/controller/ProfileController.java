package com.procs.pcs_.controller;

import com.procs.pcs_.model.SocietyEntity;
import com.procs.pcs_.model.UserData;
import com.procs.pcs_.model.UsersEntity;
import com.procs.pcs_.repository.SocietyRepository;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.repository.UserRepository;
import com.procs.pcs_.request_response.UserList;
import com.procs.pcs_.security.JwtUtils;
import com.procs.pcs_.service.SocietyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(value = "http://localhost:8081")
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;
    private final SocietyService societyService;

    @GetMapping("/progr")
    public String userAccess(@RequestHeader("Authorization") String token) {
        return "proger";
    }

    @GetMapping("/manag")
    public String managAccess() {
        return "manager";
    }

    @GetMapping("/admin")
    public ResponseEntity<?> adminAccess(@RequestHeader("Authorization") String token) {
        String username = this.getUsernameFromToken(token);
        UsersEntity user = userRepository.findByLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + username));
        SocietyEntity society = userDataRepository.getById(user.getId()).getSociety();

        if (society == null) return ResponseEntity.ok("Создайте группу или присоединитесь к существующей");
        else {
            List<UserData> users = society.getUsersList();
            List<UserList> userList = new ArrayList<>();
            users.stream().map(item ->
                    userList.add(new UserList(item.getId(),
                            userDataRepository.getById(item.getId()).getName().charAt(0),
                            userDataRepository.getById(item.getId()).getSurname(),
//                            item.getRoles(),
                            userRepository.getByLogin(item.getEmail()).getRoles()
                    )));
            return ResponseEntity.ok(userList);
        }

    }
    public String getUsernameFromToken (String token) {
        String jwt = token.substring(7, token.length()); //очищаем токен
        return jwtUtils.getUserNameFromJwtToken(jwt); //извлекаем имя пользователя из токена
    }
}
