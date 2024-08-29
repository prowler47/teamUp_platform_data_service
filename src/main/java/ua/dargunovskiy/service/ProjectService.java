package ua.dargunovskiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dargunovskiy.dao.Dao;
import ua.dargunovskiy.dao.ProjectDao;
import ua.dargunovskiy.dto.ProjectDto;
import ua.dargunovskiy.dto.UserDto;
import ua.dargunovskiy.entity.Founder;
import ua.dargunovskiy.entity.Participant;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.entity.User;
import ua.dargunovskiy.util.ProjectDtoUtil;
import ua.dargunovskiy.util.UserDtoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {
    @Autowired
    private ProjectDao projectDao;

    // add new project (for development using)
    public void addProject(Project project) throws RuntimeException {
        projectDao.add(project);
    }

    // get full list of projects
    public List<Project> getAllProjects()  {
        return projectDao.getAll();
    }

    public List<ProjectDto> getAllProjectsDto() {
        List<Project> projectList = getAllProjects();
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Project project : projectList) {
            projectDtoList.add(ProjectDtoUtil.fromProjectToDto(project.getId(), project.getName(), project.getDescription()));
        }
        return projectDtoList;
    }

    // delete project by id (for development using)
    public void removeProject(UUID id) throws RuntimeException {
        projectDao.delete(id);
    }

    // get full list of users which take part of this project by project id
    public List<UserDto> getAllUsersFromProject(UUID id) {
       var project = projectDao.getProjectById(id);
        List<Participant> participantsFromProject = project.getParticipants();
        List<UserDto> usersFromProject = new ArrayList<>();
        for (Participant participant : participantsFromProject) {
            UserDto userDto = UserDtoUtil.fromUserToUserDto(participant.getUser().getId(), participant.getUser().getFirstName(),
                    participant.getUser().getLastName(), participant.getUser().getSpeciality());
            usersFromProject.add(userDto);
        }
        return usersFromProject;
    }
}
