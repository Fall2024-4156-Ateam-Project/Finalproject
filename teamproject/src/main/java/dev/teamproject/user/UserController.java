package dev.teamproject.user;

import dev.teamproject.apiResponse.GenericApiResponse;
import dev.teamproject.user.DTOs.UserCreationRequestDTO;
import dev.teamproject.user.DTOs.UserSuccessResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Router Controller for managing User entities. This interface provides methods for retrieving
 * users by name, email, and ordering users by their UID in descending order.
 */

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }


  /**
   * To register a user given a user Creation Request.
   */
  @PostMapping("/register")
  public ResponseEntity<GenericApiResponse<UserSuccessResponseDTO>> register(
      @Valid @RequestBody UserCreationRequestDTO userCreationRequestDto) {
    UserSuccessResponseDTO userSuccessResponseDto = userService.registerUser(
        userCreationRequestDto);
    GenericApiResponse<UserSuccessResponseDTO> response = new GenericApiResponse<>(
        "User created successfully", userSuccessResponseDto, true);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * To delete a user given a uid.
   */
  @DeleteMapping("/delete/{uid}")
  public ResponseEntity<GenericApiResponse<UserSuccessResponseDTO>> deleteUser(
      @PathVariable Integer uid) {
    UserSuccessResponseDTO userSuccessResponseDto = userService.deleteUser(uid);
    GenericApiResponse<UserSuccessResponseDTO> response = new GenericApiResponse<>(
        "User deleted successfully",
        userSuccessResponseDto, true);
    return new ResponseEntity<>(response, HttpStatus.OK);
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
