package impl;

import interfaces.UsersRepository;
import models.User;
import service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
        //optional нужен если есть пустой результат
    }
}

