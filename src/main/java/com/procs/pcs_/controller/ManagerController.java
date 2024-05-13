package com.procs.pcs_.controller;

import com.procs.pcs_.model.ProjectEntity;
import com.procs.pcs_.model.SocietyEntity;
import com.procs.pcs_.model.UserData;
import com.procs.pcs_.repository.ProjectRepository;
import com.procs.pcs_.repository.SocietyRepository;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.request_response.MessageResponse;
import com.procs.pcs_.request_response.UserList;
import com.procs.pcs_.request_response.UserRequest;
import com.procs.pcs_.security.JwtUtils;
import com.procs.pcs_.service.ProjectService;
import com.procs.pcs_.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(value = "http://localhost:8081")
@RestController
@RequestMapping("/manag")
@RequiredArgsConstructor
public class ManagerController {
    private final JwtUtils jwtUtils;
    private final ProjectService projectService;
    private final UserDataRepository userDataRepository;
    private final SocietyRepository societyRepository;
    private final ProjectRepository projectRepository;
    private final UserService userService;

    @PostMapping("/addproject")
    public ResponseEntity<?> addNewProject(@RequestHeader("Authorization") String token,
                                           @RequestBody MessageResponse nameProject) {
        String login = this.getUsernameFromToken(token);
        if (projectService.addProject(nameProject.getMessage(), login))
            return ResponseEntity.ok(new MessageResponse("Проект создан"));
        else return ResponseEntity.ok(new MessageResponse("Произошла ошибка"));
    }

    @PostMapping("/delproject")
    public ResponseEntity<?> deleteProject(@RequestHeader("Authorization") String token,
                                           @RequestBody UserRequest project) {
        String login = this.getUsernameFromToken(token);
        if (projectService.delProject(project.getId(), project.getData(), login))
            return ResponseEntity.ok(new MessageResponse("Проект удален"));
        else return ResponseEntity.ok(new MessageResponse("Произошла ошибка при удалении проекта"));
    }

    @PostMapping("/listusers")
    public ResponseEntity<?> outputListOfUsers(@RequestHeader("Authorization") String token,
                                               @RequestBody UserRequest project) {
        String login = this.getUsernameFromToken(token);
//        найти проект, вывести список программистов, не участсвующих пока в проекте
        UserData manag = userDataRepository.findUserDataByEmail(login);
        SocietyEntity society = manag.getSociety();
        ProjectEntity currentProject = projectRepository.findByIdAndName
                (project.getId(), project.getData()).orElse(null);
        if (currentProject != null) {
            List<UserData> freeWorkers = society.getUsersList();
            freeWorkers.removeAll(currentProject.getWorkers());//удалить участников проекта
            freeWorkers = userService.userListWithoutAdmins(freeWorkers);//очистить от админов

            List<UserList> list = new ArrayList<>();
            for (UserData user : freeWorkers) {
                list.add(new UserList(user.getId(), user.getName().charAt(1),
                        user.getSurname(), null));
            }
            return ResponseEntity.ok(list);
        } else return ResponseEntity.ok(new MessageResponse("Ошибка вывода спика пользователей"));
    }
//    @PostMapping("/addusertoproject")
//    public ResponseEntity<?> addUserToProject(@RequestHeader("Authorization") String token) {
//
//    }

    private String getUsernameFromToken(String token) {
        String jwt = token.substring(7, token.length()); //очищаем токен
        return jwtUtils.getUserNameFromJwtToken(jwt); //извлекаем имя пользователя из токена
    }
}
