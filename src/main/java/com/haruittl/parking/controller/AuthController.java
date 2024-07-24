package com.haruittl.parking.controller;

import com.haruittl.parking.dto.request.LoginRequest;
import com.haruittl.parking.dto.request.SignupRequest;
import com.haruittl.parking.dto.response.JwtResponse;
import com.haruittl.parking.dto.response.MessageResponse;
import com.haruittl.parking.entity.User;
import com.haruittl.parking.entity.UserRole;
import com.haruittl.parking.util.JwtUtil;
import com.haruittl.parking.security.UserDetailsImpl;
import com.haruittl.parking.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Log4j2
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtil jwtUtil;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    log.info("Authenticating user: " + loginRequest.getUsername());
    log.info("Authenticating password: " + encoder.encode(loginRequest.getPassword()));

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));
    if (!authentication.isAuthenticated()) {
      log.info("Authentication failed for user: " + loginRequest.getUsername());
      return ResponseEntity.badRequest().body("Error: Invalid credentials!");
    }

    log.info("Authentication successful for user: " + loginRequest.getUsername());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.info("User authenticated successfully: " + loginRequest.getUsername());
    String jwt = jwtUtil.generateToken(authentication);
    log.info("Generated JWT: " + jwt);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    log.info("User's ID: " + userDetails.getId());
    log.info("User's username: " + userDetails.getUsername());
    log.info("User's email: " + userDetails.getEmail());
    log.info("User's role: " + userDetails.getAuthorities().iterator().next().getAuthority());

    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        userDetails.getAuthorities().iterator().next().getAuthority()));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userService.existsByUsername(signUpRequest.getUsername())) {
      log.info("Attempting to register existing user: " + signUpRequest.getUsername());
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }

    if (userService.existsByEmail(signUpRequest.getEmail())) {
      log.info("Attempting to register existing email: " + signUpRequest.getEmail());
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

    user.setFullName(signUpRequest.getFullName());
    user.setRole(UserRole.USER);

    userService.saveUser(user);

    log.info("User registered: " + signUpRequest.getUsername());
    log.info("User's role: " + user.getRole());
    log.info("User's email: " + user.getEmail());
    log.info("User's password: " + signUpRequest.getPassword());
    return ResponseEntity.ok("User registered successfully!");
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    // 여기서는 클라이언트 측에서 토큰을 삭제하는 것을 가정합니다.
    // 추가적인 서버 측 로직이 필요하다면 여기에 구현합니다.
    return ResponseEntity.ok(new MessageResponse("로그아웃 성공!"));
  }
}