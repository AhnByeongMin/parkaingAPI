package com.haruittl.parking.service;

import com.haruittl.parking.entity.User;
import com.haruittl.parking.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User user) {
    // 비밀번호를 BCrypt로 인코딩
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User updateUser(User user) {
    return userRepository.save(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }
}