package dev.teamproject.user;

import dev.teamproject.exceptionHandler.UserNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing User entities. This class provides methods
 * for retrieving users by different criteria, as well as saving user data.
 */

@Service
public class UserService {
  private final UserRepo userRepo;
  
  @Autowired
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }
  
  // Some Get ops
  public List<User> getAllUsers() {
    return this.userRepo.findAllByOrderByUidDesc();
  }
  
  public User findById(int uid) {
    return this.userRepo
        .findById(uid)
        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + uid));
  }
  
  public List<User> findByEmail(String email) {
    return this.userRepo.findByEmail(email);
  }
  
  public List<User> findByName(String username) {
    return this.userRepo.findByName(username);
  }
  
  // Add ops
  public void save(User user) {
    this.userRepo.save(user);
  }


  
  
}
