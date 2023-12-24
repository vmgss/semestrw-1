package service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    void deleteUser(Long userId);

    void changeUserRole(Long userId, String newRole);
}

