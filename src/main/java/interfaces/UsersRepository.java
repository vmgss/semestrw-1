package interfaces;

import models.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {

    public abstract void save(User entity);
    Optional<User> findByUsername(String username);


    String getUserHashedPassword(String username);
}
