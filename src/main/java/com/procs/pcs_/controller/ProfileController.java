package com.procs.pcs_.controller;

import com.procs.pcs_.model.ProjectEntity;
import com.procs.pcs_.model.SocietyEntity;
import com.procs.pcs_.model.UserData;
import com.procs.pcs_.model.UsersEntity;
import com.procs.pcs_.repository.SocietyRepository;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.repository.UserRepository;
import com.procs.pcs_.request_response.UserList;
import com.procs.pcs_.security.JwtUtils;
import com.procs.pcs_.service.ProjectService;
import com.procs.pcs_.service.SocietyService;
import com.procs.pcs_.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(value = "http://localhost:8081")
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final JwtUtils jwtUtils;
    private final SocietyService societyService;
    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping("/progr")
    public String userAccess(@RequestHeader("Authorization") String token) {
        return "proger";
    }

    @GetMapping("/manag")
    public JSONObject managAccess(@RequestHeader("Authorization") String token) {
//        колво проектов,
//        из json статус и дата создания проекта
//        колво просроченных задач по всем проектам,
//        колво неначатых задач
//        колво задач в процессе
        List<ProjectEntity> projects = projectService.getListOfWorkedProjects(getUsernameFromToken(token));
        JSONObject json = new JSONObject();
        for (ProjectEntity elem : projects) {
            elem.setUser(null);
            elem.setWorkers(null);
        }
        json.put("count", projects.size());
        json.put("projects", projects);

        return json;
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
