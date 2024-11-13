package dev.teamproject.user;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing User entities. This interface provides
 * methods for retrieving users by name, email, and ordering users by their UID in
 * descending order.
 */

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
  List<User> findByName(String username);

  List<User> findByUid(Integer uid);
  
  List<User> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByUid(int uid);
  
  List<User> findAllByOrderByUidDesc();
  
}
