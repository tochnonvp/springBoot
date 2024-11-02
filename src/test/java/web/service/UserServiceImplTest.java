package web.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import web.dto.UserDto;
import web.exceptions.UserAlreadyExistsException;
import web.exceptions.UserNotFoundException;
import web.mapper.UserMapper;
import web.model.User;
import web.repository.UserRepository;
import web.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John", "Doe", 30, 1); // Инициализация тестового пользователя
    }

    @Test
    void testFindAll() {
        // Тестирует, что метод findAll возвращает список пользователей
        when(userService.findAll()).thenReturn(Collections.singletonList(user));

        List<User> users = userService.findAll();

        assertEquals(1, users.size());
        assertEquals("John", users.get(0).getName());
        verify(userRepository).findAll();
    }

    @Test
    void testFindById_UserExists() {
        // Тестирует, что метод findById возвращает пользователя при его существовании
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertEquals(user, foundUser);
    }

    @Test
    void testFindById_UserNotFound() {
        // Тестирует, что метод findById выбрасывает исключение, если пользователь не найден
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    void testSave_UserSaved() {
        // Тестирует, что метод save корректно сохраняет нового пользователя
        when(userRepository.existsByName("John")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.save(user);

        assertEquals(user, savedUser);
        verify(userRepository).save(user);
    }

    @Test
    void testSave_UserAlreadyExists() {
        // Тестирует, что метод save выбрасывает исключение, если пользователь уже существует
        when(userRepository.existsByName("John")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.save(user));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdate_UserExists() {
        // Тестирует, что метод update корректно обновляет существующего пользователя
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.update(1L, user);

        assertEquals(user, updatedUser);
        verify(userRepository).save(user);
    }

    @Test
    void testUpdate_UserNotFound() {
        // Тестирует, что метод update выбрасывает исключение, если пользователь не найден
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.update(1L, user));
    }

    @Test
    void testPatchUser_UserExists() {
        // Тестирует, что метод patchUser корректно обновляет поля существующего пользователя
        UserDto userDto = UserDto.builder()
                .name("Jane")
                .surname("Smith")
                .age(25)
                .companyId(1)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.updateUserFromPatchDto(any(UserDto.class), any(User.class))).thenAnswer(invocation -> {
            User existingUser = invocation.getArgument(1);
            existingUser.setName(userDto.getName());
            existingUser.setSurname(userDto.getSurname());
            existingUser.setAge(userDto.getAge());
            return null;
        });

        User patchedUser = userService.patchUser(1L, userDto);

        assertEquals("Jane", patchedUser.getName());
        verify(userRepository).save(patchedUser);
    }

    @Test
    void testPatchUser_UserNotFound() {
        // Тестирует, что метод patchUser выбрасывает исключение, если пользователь не найден
        UserDto userDto = UserDto.builder()
                .name("Jane")
                .surname("Smith")
                .age(25)
                .companyId(1)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.patchUser(1L, userDto));
    }

    @Test
    void testDelete_UserExists() {
        // Тестирует, что метод delete корректно удаляет существующего пользователя
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.delete(1L);

        assertTrue(result);
        verify(userRepository).delete(user);
    }

    @Test
    void testDelete_UserNotFound() {
        // Тестирует, что метод delete не удаляет пользователя, если он не найден
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = userService.delete(1L);

        assertFalse(result);
        verify(userRepository, never()).delete(any(User.class));
    }
}