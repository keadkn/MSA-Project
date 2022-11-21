package com.example.userservice.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, length = 50, unique = false)
  private String Email;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, unique = false)
  private String userId;

  @Column(nullable = false, unique = false)
  private String encryptedPwd;

}
