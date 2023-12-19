package service;

import interfaces.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

public interface SignInService {
    boolean signIn(String username, String password);
}

