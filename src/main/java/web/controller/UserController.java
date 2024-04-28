package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;


@Controller
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/")
    public String getAllUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/create")
    public String createUserForm(@ModelAttribute("user") User user) {
        return "create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user")User user) {
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/user/{id}")
    public String getUser(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "one_user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserForm(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("allowDelete", true);
        model.addAttribute("user", user);
        return "one_user";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/";
    }
}