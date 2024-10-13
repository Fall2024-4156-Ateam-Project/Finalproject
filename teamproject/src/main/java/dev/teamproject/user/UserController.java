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
    }

    @GetMapping("/findByName")
    public List<User> findByName(@RequestParam("name") String name) {
        return userService.findByName(name);
    }

    @GetMapping("/findById")
    public User findByEmail(@RequestParam("Id") int uid) {
        return userService.findById(uid);
    }

    @GetMapping("/findByEmail")
    public List<User> findByEmail(@RequestParam("email") String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/get_all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
