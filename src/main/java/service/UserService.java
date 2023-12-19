package service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String username);
}

