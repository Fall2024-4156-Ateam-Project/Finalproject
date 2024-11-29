package dev.teamproject.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.teamproject.exceptionHandler.UserException;
import dev.teamproject.exceptionHandler.UserNotFoundException;
import dev.teamproject.user.User;
import dev.teamproject.user.UserRepo;
import dev.teamproject.user.UserService;
import dev.teamproject.user.DTOs.UserCreationRequestDTO;
import dev.teamproject.user.DTOs.UserSuccessResponseDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// import static org.mockito.Mockito.*;

class UserServiceUnitTests {

  @Mock private UserRepo userRepo;

  @InjectMocks private UserService userService;

  private User user1;
  private User user2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    user1 = new User("test1", "test1@email.com");
    user2 = new User("test2", "test2@email.com");
  }

  @Test
  void testGetAllUsers() {
    List<User> users = Arrays.asList(user1, user2);
    when(userRepo.findAllByOrderByUidDesc()).thenReturn(users);

    List<User> result = userService.getAllUsers();

    assertEquals(2, result.size());
    assertEquals("test1", result.get(0).getName());
    assertEquals("test2", result.get(1).getName());
    verify(userRepo, times(1)).findAllByOrderByUidDesc();
  }

  @Test
  void testFindById() {
    when(userRepo.findById(1)).thenReturn(Optional.of(user1));

    User result = userService.findById(1);

    assertEquals("test1", result.getName());
    verify(userRepo, times(1)).findById(1);
  }

  @Test
  void testFindByEmail() {
    List<User> users = Arrays.asList(user1);
    when(userRepo.findByEmail("test1@email.com")).thenReturn(users);

    List<User> result = userService.findByEmail("test1@email.com");

    assertEquals(1, result.size());
    assertEquals("test1", result.get(0).getName());
    verify(userRepo, times(1)).findByEmail("test1@email.com");
  }

  @Test
  void testFindByName() {
    List<User> users = Arrays.asList(user2);
    when(userRepo.findByName("test2")).thenReturn(users);

    List<User> result = userService.findByName("test2");

    assertEquals(1, result.size());
    assertEquals("test2", result.get(0).getName());
    verify(userRepo, times(1)).findByName("test2");
  }

  @Test
  void testExistsByEmail() {
    when(userRepo.existsByEmail("test1@email.com")).thenReturn(true);

    boolean exists = userService.existsByEmail("test1@email.com");

    assertTrue(exists);
    verify(userRepo, times(1)).existsByEmail("test1@email.com");
  }

  @Test
  void testRegisterUser_Success() {
    UserCreationRequestDTO request = new UserCreationRequestDTO();
    request.setName("test3");
    request.setEmail("test3@email.com");

    when(userRepo.existsByEmail("test3@email.com")).thenReturn(false);
    when(userRepo.save(any(User.class))).thenAnswer(invocation -> {
      User savedUser = invocation.getArgument(0);
      // savedUser.setUid(3); // Simulate saving
      return savedUser;
    });

    UserSuccessResponseDTO response = userService.registerUser(request);

    assertEquals("test3", response.getName());
    verify(userRepo, times(1)).existsByEmail("test3@email.com");
    verify(userRepo, times(1)).save(any(User.class));
  }

  @Test
  void testRegisterUser_UserAlreadyExists() {
    UserCreationRequestDTO request = new UserCreationRequestDTO();
    request.setName("test3");
    request.setEmail("test3@email.com");

    when(userRepo.existsByEmail("test3@email.com")).thenReturn(true);

    assertThrows(UserException.class, () -> userService.registerUser(request));
    verify(userRepo, times(1)).existsByEmail("test3@email.com");
    verify(userRepo, times(0)).save(any(User.class));
  }

  @Test
  void testDeleteUser_Success() {
    when(userRepo.existsByUid(1)).thenReturn(true);
    when(userRepo.findByUid(1)).thenReturn(Arrays.asList(user1));

    UserSuccessResponseDTO response = userService.deleteUser(1);

    assertEquals("test1", response.getName());
    verify(userRepo, times(1)).existsByUid(1);
    verify(userRepo, times(1)).deleteById(1);
  }

  @Test
  void testDeleteUser_NotFound() {
    when(userRepo.existsByUid(99)).thenReturn(false);

    assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99));
    verify(userRepo, times(1)).existsByUid(99);
    verify(userRepo, times(0)).deleteById(anyInt());
  }

//  @Test
//  void testSaveUser() {
//    userService.save(user1);
//    verify(userRepo, times(1)).save(user1);
//  }
}
