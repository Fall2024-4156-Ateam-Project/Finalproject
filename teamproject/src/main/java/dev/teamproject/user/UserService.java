package dev.teamproject.user;

import dev.teamproject.common.JwtUtil;
import dev.teamproject.user.DTOs.UserErrorResponseDTO;
import dev.teamproject.user.DTOs.UserLoginRequestDTO;
import dev.teamproject.user.DTOs.UserLoginResponseDTO;
import dev.teamproject.user.DTOs.UserSuccessResponseDTO;
import dev.teamproject.exceptionHandler.UserException;
import dev.teamproject.exceptionHandler.UserNotFoundException;
import dev.teamproject.user.DTOs.UserCreationRequestDTO;
import jakarta.transaction.Transactional;
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

  @Autowired
  private JwtUtil jwtUtil;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
    return this.userRepo.findByEmail(email.toLowerCase());
  }

  public List<User> findByName(String username) {
    return this.userRepo.findByName(username);
  }

  // Deprecated: use registerUser instead
  private void save(User user) {
    this.userRepo.save(user);
  }


  public boolean existsByEmail(String email) {
    return this.userRepo.existsByEmail(email.toLowerCase());
  }

  /**
   * To register a user with given info
   *
   * @param userCreationRequestDTO
   * @return
   */
  @Transactional
  public UserSuccessResponseDTO registerUser(UserCreationRequestDTO userCreationRequestDTO) {
    if (existsByEmail(userCreationRequestDTO.getEmail())) {
      //set the error response
      UserErrorResponseDTO userErrorResponseDTO = new UserErrorResponseDTO();
      userErrorResponseDTO.setUserResponseFromUserCreationDTO(userCreationRequestDTO);
      throw new UserException("User already exist", userErrorResponseDTO);
    }
    User user = new User();
    user.setName(userCreationRequestDTO.getName());
    user.setEmail(userCreationRequestDTO.getEmail());
    user.setPassword_hash(passwordEncoder.encode(userCreationRequestDTO.getPassword()));

    this.userRepo.save(user);

    UserSuccessResponseDTO userSuccessResponseDTO = new UserSuccessResponseDTO();

    userSuccessResponseDTO.setUserResponseFromUser(user);

    return userSuccessResponseDTO;
  }

  /**
   * To delete a user
   *
   * @param uid
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

  public UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {
    if (!existsByEmail(userLoginRequestDTO.getEmail())) {
      //set the error response
      UserErrorResponseDTO userErrorResponseDTO = new UserErrorResponseDTO();
      userErrorResponseDTO.setUserResponseFromUserLoginDTO(userLoginRequestDTO);
      throw new UserException("No account found with the provided email", userErrorResponseDTO);
    }
    User user = userRepo.findByEmail(userLoginRequestDTO.getEmail()).get(0);
    if (!passwordEncoder.matches(userLoginRequestDTO.getPassword(), user.getPassword_hash())) {
      UserErrorResponseDTO userErrorResponseDTO = new UserErrorResponseDTO();
      userErrorResponseDTO.setUserResponseFromUserLoginDTO(userLoginRequestDTO);
      throw new UserException("Incorrect password", userErrorResponseDTO);
    }
    /**
     * Success
     */
    UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
    userLoginResponseDTO.setToken(jwtUtil.generateToken(userLoginRequestDTO.getEmail()));
    userLoginResponseDTO.setEmail(userLoginRequestDTO.getEmail());
    userLoginResponseDTO.setStatus("OK");

    return userLoginResponseDTO;

  }

}



