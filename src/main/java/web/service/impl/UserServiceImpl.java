package web.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.dto.UserDto;
import web.exceptions.UserAlreadyExistsException;
import web.exceptions.UserNotFoundException;
import web.mapper.UserMapper;
import web.model.User;
import web.repository.UserRepository;
import web.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    public User save(User user) {
        if (userRepository.existsByName(user.getName())) {
            throw new UserAlreadyExistsException("Пользователь с именем " + user.getName() + " уже существует");
        }
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setSurname(user.getSurname());
                    existingUser.setAge(user.getAge());
                    existingUser.setCompanyId(user.getCompanyId());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    public User patchUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userMapper.updateUserFromPatchDto(userDto, user);

        return userRepository.save(user);
    }

    public boolean delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        } else {
            return false;
        }
    }
}