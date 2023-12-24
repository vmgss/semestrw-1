package impl;

import dto.RegisterForm;
import interfaces.UsersRepository;
import models.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import service.RegisterService;

import java.sql.SQLException;

public class RegisterServiceImpl implements RegisterService{

    private UsersRepository usersRepository;

    private PasswordEncoder passwordEncoder;

    public RegisterServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void register(RegisterForm form) throws SQLException {
        User user = User.builder()
                .username(form.getUsername())
                .email(form.getEmail())
                .passwordHash(passwordEncoder.encode(form.getPasswordHash()))
                .build();

        usersRepository.save(user);
    }
}

