package dev.teamproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;
    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    // Some Get ops
    public List<User> getAllUsers() {
        return this.userRepo.findAllByOrderByUidDesc();
    }
    public User findById(int uid) {
        return this.userRepo.findById(uid).get();
    }
    public List<User> findByEmail(String email) {
        return this.userRepo.findByEmail(email).stream().toList();
    }
    // Add ops
    public void save(User user) {
        this.userRepo.save(user);
    }


}
