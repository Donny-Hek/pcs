package com.procs.pcs_.controller;

import com.procs.pcs_.repository.SocietyRepository;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.request_response.MessageResponse;
import com.procs.pcs_.request_response.UserRequest;
import com.procs.pcs_.security.JwtUtils;
import com.procs.pcs_.service.SocietyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(value = "http://localhost:8081")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
public class AdminController {
    private final JwtUtils jwtUtils;
    private final UserDataRepository userDataRepository;
    private final SocietyRepository societyRepository;
    private final SocietyService societyService;

    @PostMapping("/addgroup")
    public boolean addSociety(@RequestHeader("Authorization") String token,
                              @RequestBody MessageResponse message) {
        String login = this.getUsernameFromToken(token);
        return societyService.addSociety(message.getMessage(), login);
    }

    @PostMapping("/adduser")
    public boolean addUserToSociety(@RequestHeader("Authorization") String token,
                                    @RequestBody UserRequest request) {
        String login = this.getUsernameFromToken(token);
        return societyService.addUserToSociety(request.getId(), login, request.getEmail());
    }

    @PostMapping("/edituser/{id}")
    public boolean editUser(@PathVariable int id,
                            @RequestHeader("Authorization") String token) {

        return false;
    }

    public String getUsernameFromToken(String token) {
        String jwt = token.substring(7, token.length()); //очищаем токен
        return jwtUtils.getUserNameFromJwtToken(jwt); //извлекаем имя пользователя из токена
    }
}
