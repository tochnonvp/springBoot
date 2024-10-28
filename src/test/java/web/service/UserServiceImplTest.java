package web.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import web.model.User;
import web.repository.UserRepository;
import web.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    User user1 = new User(1L, "John", "Doe", 30,2);
    User user2 = new User(2L, "Jane", "Doe", 25,2);

    @Test
    @DisplayName("Проверка получения всех пользователей")
    void testFindAll() {

        List<User> expectedUsers = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.findAll();
        assertEquals(expectedUsers, actualUsers, "Список пользователей должен совпадать с ожидаемым");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Проверка получения пользователя по id")
    void testFindUserById() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        User actualUser = userService.findById(2L);
        assertEquals(user2, actualUser);
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Проверка на добавление пользователя")
    void testAddUser() {
        when(userRepository.save(user1)).thenReturn(user1);

        User savedUser = userRepository.save(user1);

        assertNotNull(savedUser);
        assertEquals(user1, savedUser);
        verify(userRepository, times(1)).save(user1);
    }
}
