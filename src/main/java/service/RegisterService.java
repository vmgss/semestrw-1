package service;

import dto.RegisterForm;
import interfaces.UsersRepository;
import models.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;

public interface RegisterService {
    String hashPassword(String password);
    void register(RegisterForm form) throws SQLException;

}

