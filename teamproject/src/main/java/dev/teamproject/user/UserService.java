package dev.teamproject.user;

import dev.teamproject.common.JwtUtil;
import dev.teamproject.exceptionhandler.UserException;
import dev.teamproject.exceptionhandler.UserNotFoundException;
import dev.teamproject.user.dto.UserCreationRequestDto;
import dev.teamproject.user.dto.UserErrorResponseDto;
import dev.teamproject.user.dto.UserSuccessResponseDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public UserSuccessResponseDto registerUser(UserCreationRequestDto userCreationRequestDto) {
    synchronized (lock) {
      if (existsByEmail(userCreationRequestDto.getEmail())) {
        UserErrorResponseDto userErrorResponseDto = new UserErrorResponseDto();
        userErrorResponseDto.setUserResponseFromUserCreationDto(userCreationRequestDto);
        throw new UserException("User already exists", userErrorResponseDto);
      }
      User user = new User();
      user.setName(userCreationRequestDto.getName());
      user.setEmail(userCreationRequestDto.getEmail());

      this.userRepo.save(user);

      UserSuccessResponseDto userSuccessResponseDto = new UserSuccessResponseDto();
      userSuccessResponseDto.setUserResponseFromUser(user);
      return userSuccessResponseDto;
    }
  }

  /**
   * Delete a user by UID.
   */
  @Transactional
  public UserSuccessResponseDto deleteUser(Integer uid) {
    if (!userRepo.existsByUid(uid)) {
      throw new UserNotFoundException("User not found with ID: " + uid);
    }
    User user = userRepo.findByUid(uid).get(0);

    UserSuccessResponseDto userSuccessResponseDto = new UserSuccessResponseDto();
    userSuccessResponseDto.setUserResponseFromUser(user);
    this.userRepo.deleteById(uid);
    return userSuccessResponseDto;
  }
}
