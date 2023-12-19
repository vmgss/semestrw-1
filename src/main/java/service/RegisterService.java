package service;

import interfaces.UsersRepository;
import models.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

public interface RegisterService {
    String hashPassword(String password);
}

