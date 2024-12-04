package dev.teamproject.user;

import dev.teamproject.apiResponse.GenericApiResponse;
import dev.teamproject.user.DTOs.UserCreationRequestDTO;
import dev.teamproject.user.DTOs.UserSuccessResponseDTO;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserControllerUnitTests {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;
  UserCreationRequestDTO userCreationRequestDTO;
  UserSuccessResponseDTO userSuccessResponseDTO;

  private User user1;
  private User user2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    user1 = new User("test1", "test1@email.com");
    user2 = new User("test2", "test2@email.com");
    userCreationRequestDTO = new UserCreationRequestDTO();
    userCreationRequestDTO.setName("test1");
    userCreationRequestDTO.setEmail("test1@email.com");
    userSuccessResponseDTO = new UserSuccessResponseDTO();
    userSuccessResponseDTO.setUid(1);
    userSuccessResponseDTO.setName("test1");
    userSuccessResponseDTO.setEmail("test1@email.com");

  }

  @Test
  void testRegister() {
    UserSuccessResponseDTO userSuccessResponseDTO = new UserSuccessResponseDTO();
    userSuccessResponseDTO.setName("test1");
    userSuccessResponseDTO.setEmail("test1@email.com");

    when(userService.registerUser(userCreationRequestDTO)).thenReturn(userSuccessResponseDTO);

    ResponseEntity<GenericApiResponse<UserSuccessResponseDTO>> response = userController.register(userCreationRequestDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userSuccessResponseDTO, response.getBody().getData());
    verify(userService, times(1)).registerUser(userCreationRequestDTO);
  }

  @Test
  void testDeleteUser() {
    when(userService.deleteUser(1)).thenReturn(userSuccessResponseDTO);
    ResponseEntity<GenericApiResponse<UserSuccessResponseDTO>> response = userController.deleteUser(1);
    assertEquals(1, response.getBody().getData().getUid());
    verify(userService, times(1)).deleteUser(1);
  }

  @Test
  void testFindByName() {
    List<User> users = Arrays.asList(user1);
    when(userService.findByName("test1")).thenReturn(users);

    List<User> result = userController.findByName("test1");

    assertEquals(1, result.size());
    assertEquals("test1", result.get(0).getName());
    verify(userService, times(1)).findByName("test1");
  }

  @Test
  void testFindById() {
    when(userService.findById(0)).thenReturn(user1);

    User result = userController.findById(0);

    assertEquals("test1", result.getName());
    verify(userService, times(1)).findById(0);
  }

  @Test
  void testFindByEmail() {
    List<User> users = Arrays.asList(user2);
    when(userService.findByEmail("test2@email.com")).thenReturn(users);

    List<User> result = userController.findByEmail("test2@email.com");

    assertEquals(1, result.size());
    assertEquals("test2", result.get(0).getName());
    verify(userService, times(1)).findByEmail("test2@email.com");
  }

  @Test
  void testGetAllUsers() {
    List<User> users = Arrays.asList(user1, user2);
    when(userService.getAllUsers()).thenReturn(users);

    List<User> result = userController.getAllUsers();

    assertEquals(2, result.size());
    verify(userService, times(1)).getAllUsers();
  }
}
