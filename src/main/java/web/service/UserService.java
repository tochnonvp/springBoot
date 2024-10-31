package web.service;

import web.dto.UserDto;
import web.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User update(Long id, User user);

    User patchUser(Long id, UserDto userDto);

    boolean delete(Long id);
}