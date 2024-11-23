package dev.teamproject.user;

import dev.teamproject.common.JwtUtil;
import dev.teamproject.user.DTOs.UserErrorResponseDTO;
import dev.teamproject.user.DTOs.UserLoginResponseDTO;
import dev.teamproject.user.DTOs.UserSuccessResponseDTO;
import dev.teamproject.exceptionHandler.UserException;
import dev.teamproject.exceptionHandler.UserNotFoundException;
import dev.teamproject.user.DTOs.UserCreationRequestDTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing User entities. This class provides methods for retrieving users by
 * different criteria, as well as saving user data.
 */
@Service
public class UserService {

  private final UserRepo userRepo;
  private final Object lock = new Object();

  @Autowired
  private JwtUtil jwtUtil;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  /**
   * Retrieve all users.
   */
  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    return this.userRepo.findAllByOrderByUidDesc();
  }

  /**
   * Find a user by ID.
   */
  @Transactional(readOnly = true)
  public User findById(int uid) {
    return this.userRepo
            .findById(uid)
            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + uid));
  }

  /**
   * Find users by email.
   */
  @Transactional(readOnly = true)
  public List<User> findByEmail(String email) {
    return this.userRepo.findByEmail(email.toLowerCase());
  }

  /**
   * Find users by name.
   */
  @Transactional(readOnly = true)
  public List<User> findByName(String username) {
    return this.userRepo.findByName(username);
  }

  /**
   * Check if a user exists by email.
   */
  public boolean existsByEmail(String email) {
    return this.userRepo.existsByEmail(email.toLowerCase());
  }

  /**
   * Register a new user.
   */
  @Transactional
  public UserSuccessResponseDTO registerUser(UserCreationRequestDTO userCreationRequestDTO) {
    synchronized (lock) {
      if (existsByEmail(userCreationRequestDTO.getEmail())) {
        UserErrorResponseDTO userErrorResponseDTO = new UserErrorResponseDTO();
        userErrorResponseDTO.setUserResponseFromUserCreationDTO(userCreationRequestDTO);
        throw new UserException("User already exists", userErrorResponseDTO);
      }
      User user = new User();
      user.setName(userCreationRequestDTO.getName());
      user.setEmail(userCreationRequestDTO.getEmail());

      this.userRepo.save(user);

      UserSuccessResponseDTO userSuccessResponseDTO = new UserSuccessResponseDTO();
      userSuccessResponseDTO.setUserResponseFromUser(user);
      return userSuccessResponseDTO;
    }
  }

  /**
   * Delete a user by UID.
   */
  @Transactional
  public UserSuccessResponseDTO deleteUser(Integer uid) {
    if (!userRepo.existsByUid(uid)) {
      throw new UserNotFoundException("User not found with ID: " + uid);
    }
    User user = userRepo.findByUid(uid).get(0);

    UserSuccessResponseDTO userSuccessResponseDTO = new UserSuccessResponseDTO();
    userSuccessResponseDTO.setUserResponseFromUser(user);
    this.userRepo.deleteById(uid);
    return userSuccessResponseDTO;
  }
}
