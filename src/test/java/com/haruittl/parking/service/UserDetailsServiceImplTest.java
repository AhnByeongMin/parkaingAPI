package com.haruittl.parking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.haruittl.parking.entity.User;
import com.haruittl.parking.entity.UserRole;
import com.haruittl.parking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class UserDetailsServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserDetailsServiceImpl userDetailsService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenValidUsername_thenUserShouldBeFound() {
    String username = "testuser";
    User user = new User(username, "test@test.com", "password");
    user.setRole(UserRole.USER);

    when(userRepository.findByUsername(username)).thenReturn(user);

    UserDetails foundUser = userDetailsService.loadUserByUsername(username);

    assertNotNull(foundUser);
    assertEquals(username, foundUser.getUsername());
  }

  @Test
  void whenInvalidUsername_thenThrowException() {
    String username = "testuser";

    when(userRepository.findByUsername(username)).thenReturn(null);

    assertThrows(UsernameNotFoundException.class, () -> {
      userDetailsService.loadUserByUsername(username);
    });
  }
}