package web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.User;

import web.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public List<User> getAllUsers() {
        return userServiceImpl.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userServiceImpl.save(user);
    }

    @PutMapping({"/id"})
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userServiceImpl.update(id, user));
    }

    @DeleteMapping
    public void deleteUser(@PathVariable Long id) {
        userServiceImpl.delete(id);
    }
}