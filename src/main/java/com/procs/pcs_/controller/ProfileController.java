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
import com.procs.pcs_.service.UserService;
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
    private final UserService userService;

    @GetMapping("/progr")
    public String userAccess(@RequestHeader("Authorization") String token) {
        return "proger";
    }

    @GetMapping("/manag")
    public String managAccess() {
//        колво проектов,
//        из json статус и дата создания проекта
//        колво просроченных задач по всем проектам,
//        колво неначатых задач
//        колво задач в процессе
//        колво задач, требующих одобренного завершения

        return "manager";
    }

    @GetMapping("/admin")
    public ResponseEntity<?> adminAccess(@RequestHeader("Authorization") String token) {
        String login = this.getUsernameFromToken(token);
        int societyid = userService.getUserSociety(login);

        if (societyid == 0) return ResponseEntity.ok("Создайте группу или присоединитесь к существующей");
        else {
            return ResponseEntity.ok(societyService.getUserListBySocietyId(societyid, login));
        }
    }

    public String getUsernameFromToken(String token) {
        String jwt = token.substring(7, token.length()); //очищаем токен
        return jwtUtils.getUserNameFromJwtToken(jwt); //извлекаем имя пользователя из токена
    }
}
