package com.haruittl.parking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.haruittl.parking.entity.User;
import com.haruittl.parking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenSaveUser_thenReturnSavedUser() {
    User user = new User("testuser", "test@test.com", "zx187168");

    when(userRepository.save(any(User.class))).thenReturn(user);

    User savedUser = userService.createUser(user);

    assertNotNull(savedUser);
    assertEquals("testuser", savedUser.getUsername());
    verify(userRepository, times(1)).save(any(User.class));
  }

  // 다른 UserService 관련 테스트 메서드들...
}