package dev.teamproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void saveUser(@RequestBody User user) {
        userService.save(user);
        //Todo
    }

    @GetMapping("/get_all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
