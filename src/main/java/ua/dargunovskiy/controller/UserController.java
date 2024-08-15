package ua.dargunovskiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.dargunovskiy.dto.ProjectDto;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.entity.User;
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

    @GetMapping("/getProjects/{id}")
    public List<ProjectDto> getUsersProjects(@PathVariable("id") UUID id) {
        try {
            userService.getAllUsersProjectDto(id);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userService.getAllUsersProjectDto(id);
    }
}
