package web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import web.model.User;

import java.util.List;

@Tag(name = "CRUD", description = "CRUD операции для пользователей")
public interface UserController {

    @Operation(summary = "Get users", description = "Получение всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ответ на запрос всех пользователей"),
    })

    @GetMapping
    ResponseEntity<List<User>> getAllUsers();

    @Operation(summary = "Get users by id", description = "Получение пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ответ на запрос получения пользователя"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),

    })

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id);

    @Operation(summary = "Adding a user", description = "Добавление пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь добавлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),

    })

    @PostMapping
    ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult);
    @Operation(summary = "Change user", description = "Изменение пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь изменен"),
    })

    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user, BindingResult bindingResult);

    @Operation(summary = "Delete user", description = "Удаление пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален"),
    })

    @DeleteMapping("/{id}")
    boolean deleteUser(@PathVariable Long id);
}