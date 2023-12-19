package impl;

import interfaces.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import service.RegisterService;

public class RegisterServiceImpl implements RegisterService {
    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    // реализация метода hashPassword,
    // берет пароль и сгенерированную соль и хэширует
}

