package com.example.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.example.entity.User;
import com.example.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * 회원가입 메서드.
     * 사용자명이나 이메일 중복 여부를 체크한 후, 새로운 User 객체를 생성하고 저장합니다.
     */
    public void register(String username, String email, String password) {
        List<User> existingUsers = userRepository.findByUsernameOrEmail(username, email);
        if (!existingUsers.isEmpty()) {
            throw new RuntimeException("이미 존재하는 사용자명 또는 이메일입니다.");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);  // setPassword 내부에서 암호화됨
        userRepository.save(user);
        System.out.println("회원가입이 완료되었습니다.");
    }
    
    /**
     * 로그인 메서드.
     * 사용자명을 기준으로 User를 조회한 후, 평문 비밀번호와 암호화된 비밀번호를 비교합니다.
     */
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("해당 사용자가 존재하지 않습니다.");
        }
        if (BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("로그인 성공!");
            return user;
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
