package com.procs.pcs_.service;

import com.procs.pcs_.model.ProjectEntity;
import com.procs.pcs_.model.SocietyEntity;
import com.procs.pcs_.model.UserData;
import com.procs.pcs_.repository.ProjectRepository;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.request_response.UserList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final UserDataRepository userDataRepository;
    private final ProjectRepository projectRepository;
    private final UserService userService;

    @Modifying(clearAutomatically = true)
    public boolean addProject(String nameSociety, String nameManag) {
        UserData manag = userDataRepository.findUserDataByEmail(nameManag);
        ProjectEntity project = new ProjectEntity(nameSociety, manag);
        projectRepository.save(project);
        manag.addToOwnerList(project);
        userDataRepository.save(manag);
        return true;
    }

    @Modifying(clearAutomatically = true)
    public boolean delProject(int id, String name, String nameManag) {
        UserData manag = userDataRepository.findUserDataByEmail(nameManag);
//        находит проект по владельцу, названию и id
        ProjectEntity project = projectRepository.findByUserAndIdAndName
                (manag.getId(), manag.getEmail(), id, name).orElse(null);

        if (project == null) return false;
        else {
            if (project.getWorkers().isEmpty()) {//если нет работников, то
//                удаляет из списка владеемых проектов у пользователя
                manag.delFromOwnerList(project);
                userDataRepository.save(manag);
//                удаляет сам проект
                projectRepository.deleteById(project.getId());
                return true;
            } else return false;
        }
    }

    @Modifying(clearAutomatically = true)
    public List<UserData> getProjectWorkersByIdAndName(String login, int id, String name) {
        UserData manag = userDataRepository.findUserDataByEmail(login);
        SocietyEntity society = manag.getSociety();
        ProjectEntity currentProject = projectRepository.findByIdAndName
                (id, name).orElse(null);
        if (currentProject != null){
            List<UserData> freeWorkers = society.getUsersList();
            freeWorkers.removeAll(currentProject.getWorkers());//удалить участников проекта
            freeWorkers=userService.userListWithoutAdmins(freeWorkers);
            return freeWorkers;
        }
        else return null;
    }

    @Modifying(clearAutomatically = true)
    public List<ProjectEntity> getListOfWorkedProjects(String login){
        UserData manag = userDataRepository.findUserDataByEmail(login);
        return manag.getOwnerOfProjects();
    }
}

