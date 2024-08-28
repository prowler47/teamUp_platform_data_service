package ua.dargunovskiy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dargunovskiy.dao.UserRequestDao;

import java.util.UUID;

@Service
public class UserRequestService {
    @Autowired
    private UserRequestDao userRequestDao;
    public void deleteUserRequest(UUID userRequestId) {
        userRequestDao.delete(userRequestId);
    }
}
