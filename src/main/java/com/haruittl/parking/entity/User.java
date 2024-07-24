package com.haruittl.parking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "full_name")
  private String fullName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;

  // 기본 생성자
  public User() {
  }

  // 생성자, 추가 메서드 등은 필요에 따라 구현
  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = UserRole.USER; // 기본 역할을 USER로 설정
  }
}