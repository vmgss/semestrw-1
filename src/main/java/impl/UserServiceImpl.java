package impl;

import interfaces.UsersRepository;
import models.User;
import service.UserService;

import java.util.List;
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

    @Override
    public List<User> getAllUsers() {
        return usersRepository.getAllUsers();
    }

    @Override
    public void deleteUser(Long userId) {
        usersRepository.deleteUser(userId);
    }

    @Override
    public void changeUserRole(Long userId, String newRole) {
        usersRepository.changeUserRole(userId, newRole);
    }
}

