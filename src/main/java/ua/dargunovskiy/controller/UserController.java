package ua.dargunovskiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.dargunovskiy.dto.ProjectDto;
import ua.dargunovskiy.dto.UserDto;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.entity.User;
import ua.dargunovskiy.entity.UserRequest;
import ua.dargunovskiy.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getAllUsersDto")
    public List<UserDto> getAllUsersDto() {
       return userService.getAllUsersDto();
    }

    @GetMapping("/getProjects/{userId}")
    public List<ProjectDto> getUsersProjects(@PathVariable("userId") UUID userId) {
        try {
            userService.getAllUsersProjectDto(userId);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userService.getAllUsersProjectDto(userId);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
    }

    @PostMapping("/createRequestToProject/{userId}/{projectId}")
    public void createRequestToProject(@PathVariable("userId") UUID userId, @PathVariable("projectId") UUID projectId,
                                       @RequestBody UserRequest userRequest) {
        userService.createRequestToProject(projectId, userId, userRequest);
    }
}
