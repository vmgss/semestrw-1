package impl;

import interfaces.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import service.SignInService;
import org.springframework.stereotype.Service;

@Service
public class SignInServiceImpl implements SignInService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignInServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean signIn(String username, String password) {
        String hashedPasswordFromDatabase = usersRepository.getUserHashedPassword(username);

        // Проверка
        if (hashedPasswordFromDatabase != null) {
            return passwordEncoder.matches(password, hashedPasswordFromDatabase);
        }

        return false;
    }

}




