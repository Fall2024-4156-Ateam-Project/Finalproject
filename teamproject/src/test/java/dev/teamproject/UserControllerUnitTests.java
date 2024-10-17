package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

class UserControllerUnitTests {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  private User user1;
  private User user2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    user1 = new User("test1", "test1@email.com");

    user2 = new User("test2", "test2@email.com");
  }

  @Test
  void testSaveUser() {
    userController.saveUser(user1);

    verify(userService, times(1)).save(user1);
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
