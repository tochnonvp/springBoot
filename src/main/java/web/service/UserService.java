package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    User getUserById(long id);

    void updateUser(User user);

    void removeUserById(long id);

    List<User> getAllUsers();

    void saveUser (User user);
}