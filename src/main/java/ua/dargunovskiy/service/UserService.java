package ua.dargunovskiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dargunovskiy.dao.UserDao;
import ua.dargunovskiy.dao.UserRequestDao;
import ua.dargunovskiy.dto.ProjectDto;
import ua.dargunovskiy.entity.Participant;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.entity.User;
import ua.dargunovskiy.entity.UserRequest;
import ua.dargunovskiy.util.ProjectDtoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRequestDao userRequestDao;

    // add new user
    public void addUser(User user) {
        userDao.add(user);
    }

    // get full lust of users
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    // get list of project in which this user take part
    public List<Project> getAllUsersProjects(UUID userId) throws NullPointerException {
        User userById = userDao.getUserById(userId);
        List<Participant> usersParticipants = userById.getParticipants();
        List<Project> listOfUsersProjects = new ArrayList<>();
        for (Participant participant : usersParticipants) {
            listOfUsersProjects.add(participant.getProject());
        }
        return listOfUsersProjects;
    }

    // get list of short version for view of projects in which this user take part
    public List<ProjectDto> getAllUsersProjectDto(UUID userId) {
        User userById = userDao.getUserById(userId);
        List<Participant> usersParticipants = userById.getParticipants();
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Participant participant : usersParticipants) {
            ProjectDto projectDto = ProjectDtoUtil.fromProjectToDto(participant.getProject().getId(), participant.getProject().getName(), participant.getProject().getDescription());
            projectDtoList.add(projectDto);
        }
        return projectDtoList;
    }

    // delete user by id
    public void deleteUser(UUID userId) {
        userDao.delete(userId);
    }

    public void createRequestToProject(UUID projectId, UUID userId, UserRequest userRequest) {
        userRequest.setProjectId(projectId);
        User userById = userDao.getUserById(userId);
        userRequest.setUser(userById);
        userRequestDao.add(userRequest);
    }
}
