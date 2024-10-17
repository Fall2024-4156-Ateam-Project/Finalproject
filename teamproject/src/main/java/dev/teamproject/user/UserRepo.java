package dev.teamproject.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
  List<User> findByName(String username);
  
  List<User> findByEmail(String email);
  
  List<User> findAllByOrderByUidDesc();
  
}
