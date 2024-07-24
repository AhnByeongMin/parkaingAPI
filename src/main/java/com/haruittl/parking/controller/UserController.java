package com.haruittl.parking.controller;

import com.haruittl.parking.entity.User;
import com.haruittl.parking.exception.ResourceNotFoundException;
import com.haruittl.parking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/me")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> getCurrentUser(@RequestParam String username) {
    User user = userService.getUserByUsername(username);
    if (user == null) {
      throw new ResourceNotFoundException("User not found with username: " + username);
    }
    user.setPassword(null);
    return ResponseEntity.ok(user);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    User user = userService.getUserById(id);
    if (user == null) {
      throw new ResourceNotFoundException("User not found with id: " + id);
    }
    user.setPassword(null);
    return ResponseEntity.ok(user);
  }
}