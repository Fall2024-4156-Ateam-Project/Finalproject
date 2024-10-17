package dev.teamproject.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Router Controller for managing User entities. This interface provides
 * methods for retrieving users by name, email, and ordering users by their UID in
 * descending order.
 */

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
  public User findById(@RequestParam("Id") int uid) {
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
