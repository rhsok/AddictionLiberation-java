/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.entity;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ra
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
      // 기본 생성자: 새로운 User 객체 생성 시 고유 ID 부여
    public User() {
        this.id = UUID.randomUUID().toString();
    }
    
    // Getter와 Setter
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String setName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    /**
     * 비밀번호를 설정할 때 평문을 바로 저장하지 않고, BCrypt를 사용해 암호화하여 저장합니다.
     */
    public void setPassword(String password) {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
