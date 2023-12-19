package impl;

import interfaces.UsersRepository;
import models.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import secure.AuthManager;

import java.util.Optional;

public class AuthManagerImpl implements AuthManager {
    //реализация интерфейса AuthManager
    private final UsersRepository usersRepository;

    public AuthManagerImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean authenticate(String username, String password) {
        Optional<User> user = usersRepository.findByUsername(username);
        //Используем для поиска по имени

        if (user.isPresent()) {
            String hashedPasswordFromDatabase = user.get().getPasswordHash();
            String enteredPasswordHash = hashPassword(password);

            return hashedPasswordFromDatabase.equals(enteredPasswordHash);
        }// Если пользователь есть, то сравниваем хэш из бд с хэшем введенного пароля
        //если совпадают то true

        return false;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
        // Хэширует пароль с BCrypt
        // генерирует соль (рандомную строку) и делает уникальные хэши
    }
}
