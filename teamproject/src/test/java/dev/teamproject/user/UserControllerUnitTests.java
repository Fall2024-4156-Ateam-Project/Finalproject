package dev.teamproject.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.teamproject.apiResponse.GenericApiResponse;
import dev.teamproject.user.DTOs.UserCreationRequestDTO;
import dev.teamproject.user.DTOs.UserSuccessResponseDTO;
import dev.teamproject.user.User;
import dev.teamproject.user.UserController;
import dev.teamproject.user.UserService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserControllerUnitTests {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;
  UserCreationRequestDTO userCreationRequestDto;
  UserSuccessResponseDTO userSuccessResponseDto;

  private User user1;
  private User user2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    user1 = new User("test1", "test1@email.com");
    user2 = new User("test2", "test2@email.com");
    userCreationRequestDto = new UserCreationRequestDTO();
    userCreationRequestDto.setName("test1");
    userCreationRequestDto.setEmail("test1@email.com");
    userSuccessResponseDto = new UserSuccessResponseDTO();
    userSuccessResponseDto.setUid(1);
    userSuccessResponseDto.setName("test1");
    userSuccessResponseDto.setEmail("test1@email.com");
    // userSuccessResponseDTO.setCreatedAt(new Timestamp(System.currentTimeMillis()));

  }

  //  @Test
  //  void testSaveUser() {
  //    userController.saveUser(user1);
  //
  //    verify(userService, times(1)).save(user1);
  //  }

  @Test
  void testRegister() {
    UserSuccessResponseDTO userSuccessResponseDto = new UserSuccessResponseDTO();
    userSuccessResponseDto.setName("test1");
    userSuccessResponseDto.setEmail("test1@email.com");

    when(userService.registerUser(userCreationRequestDto)).thenReturn(userSuccessResponseDto);

    ResponseEntity<GenericApiResponse<UserSuccessResponseDTO>> response
            = userController.register(userCreationRequestDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    // Verify HTTP status code
    // assertEquals("User created successfully", response.getBody().getMessage());
    // Verify response message
    assertEquals(userSuccessResponseDto, response.getBody().getData());
    // Verify returned user details
    verify(userService,
            times(1)).registerUser(userCreationRequestDto);
  }

  @Test
  void testDeleteUser() {
    // UserSuccessResponseDTO responseDTO
    // = new UserSuccessResponseDTO(1, "test1", "test1@email.com");
    when(userService.deleteUser(1)).thenReturn(userSuccessResponseDto);

    ResponseEntity<GenericApiResponse<UserSuccessResponseDTO>> response
            = userController.deleteUser(1);

    // assertEquals("User deleted successfully", response.getBody().getMessage());
    assertEquals(1, response.getBody().getData().getUid());
    verify(userService, times(1)).deleteUser(1);
  }

  @Test
  void testFindByName() {
    List<User> users = Arrays.asList(user1);
    when(userService.findByName("test1")).thenReturn(users);

    List<User> result = userController.findByName("test1");

    assertEquals(1, result.size()); // Verify the size
    assertEquals("test1", result.get(0).getName()); // Verify the name
    verify(userService, times(1)).findByName("test1");
  }

  @Test
  void testFindById() {
    when(userService.findById(0)).thenReturn(user1);

    User result = userController.findById(0);

    assertEquals("test1", result.getName()); // Verify the name
    verify(userService, times(1)).findById(0);
  }

  @Test
  void testFindByEmail() {
    List<User> users = Arrays.asList(user2);
    when(userService.findByEmail("test2@email.com")).thenReturn(users);

    List<User> result = userController.findByEmail("test2@email.com");

    assertEquals(1, result.size()); // Verify the size
    assertEquals("test2", result.get(0).getName()); // Verify the name
    verify(userService, times(1)).findByEmail("test2@email.com");
  }

  @Test
  void testGetAllUsers() {
    List<User> users = Arrays.asList(user1, user2);
    when(userService.getAllUsers()).thenReturn(users);

    List<User> result = userController.getAllUsers();

    assertEquals(2, result.size()); // Verify the size
    verify(userService, times(1)).getAllUsers();
  }
}
