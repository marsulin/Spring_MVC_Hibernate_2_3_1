package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/index";
    }

    @GetMapping("/")
    public String show(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user) {

        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/")
    public String edit(Model model, @RequestParam("id") Long id) {
        model.addAttribute("user", userService.show(id));
        return "users/edit";
    }

    @PatchMapping("/")
    public String update(@ModelAttribute("user") User user,
        @RequestParam("id") Long id) {

        userService.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/")
    public String delete(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

}