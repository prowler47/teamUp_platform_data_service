package ua.dargunovskiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.dargunovskiy.dto.UserDto;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.entity.User;
import ua.dargunovskiy.service.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/addProject")
    public void addProject(@RequestBody Project project) {
//        try {
            projectService.addProject(project);
//        } catch (RuntimeException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
    }

    @GetMapping("/getAllProjects")
    public List<Project> getProjects() {
       return projectService.getAllProjects();
    }

    @GetMapping("/getAllUsersFromProject/{projectId}")
    public List<UserDto> getUsersFromProject(@PathVariable("projectId") UUID projectId) {
        return projectService.getAllUsersFromProject(projectId);
    }
    @DeleteMapping("/removeProject/{id}")
    public void removeProject(@PathVariable("id") UUID id) {
        try {
            projectService.removeProject(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
