package ua.dargunovskiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dargunovskiy.service.UserRequestService;

import java.util.UUID;

@RestController
@RequestMapping("/userRequests")
public class UserRequestController {
    @Autowired
    private UserRequestService userRequestService;

    @DeleteMapping("/deleteUserRequest/{userRequestId}")
    public void deleteUserRequest(@PathVariable("userRequestId") UUID userRequestId) {
        userRequestService.deleteUserRequest(userRequestId);
    }
}
