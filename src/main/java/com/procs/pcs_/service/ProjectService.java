package com.procs.pcs_.service;

import com.procs.pcs_.model.ProjectEntity;
import com.procs.pcs_.model.UserData;
import com.procs.pcs_.repository.ProjectRepository;
import com.procs.pcs_.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final UserDataRepository userDataRepository;
    private final ProjectRepository projectRepository;

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
}

