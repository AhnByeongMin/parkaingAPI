package com.haruittl.parking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haruittl.parking.dto.request.LoginRequest;
import com.haruittl.parking.dto.request.SignupRequest;
import com.haruittl.parking.util.JwtUtil;
import com.haruittl.parking.security.UserDetailsImpl;
import com.haruittl.parking.service.UserDetailsServiceImpl;
import com.haruittl.parking.service.UserService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserDetailsServiceImpl userDetailsService;

  @MockBean
  private UserService userService;

  @MockBean
  private AuthenticationManager authenticationManager;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @MockBean
  private JwtUtil jwtUtil;


  @Test
  void whenValidInput_thenReturns200() throws Exception {
    SignupRequest signupRequest = new SignupRequest();
    signupRequest.setUsername("testuser");
    signupRequest.setEmail("test@test.com");
    signupRequest.setPassword("password");
    signupRequest.setFullName("Test User");

    // Mock userService methods
    Mockito.when(userService.existsByUsername(Mockito.anyString())).thenReturn(false);
    Mockito.when(userService.existsByEmail(Mockito.anyString())).thenReturn(false);

    mockMvc.perform(post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signupRequest)))
        .andExpect(status().isOk());

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("testuser");
    loginRequest.setPassword("password");

    // Create UserDetails
    UserDetailsImpl userDetails = new UserDetailsImpl(1L, "testuser", "test@test.com", "password",
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

    // Mock UserDetailsService
    Mockito.when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

    // Mock AuthenticationManager
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());
    Mockito.when(
            authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);

    // Mock JwtUtil
    Mockito.when(jwtUtil.generateToken(Mockito.any(Authentication.class)))
        .thenReturn("mocked-jwt-token");

    mockMvc.perform(post("/api/auth/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.username").value("testuser"))
        .andExpect(jsonPath("$.email").value("test@test.com"));
  }

  @Test
  void whenLogout_thenReturns200() throws Exception {
    mockMvc.perform(post("/api/auth/signout")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("로그아웃 성공!"));
  }

  @Test
  void password_thenReturns200() throws Exception {
    String password = passwordEncoder.encode("password");
    assertEquals("zx187168", password);

  }


}