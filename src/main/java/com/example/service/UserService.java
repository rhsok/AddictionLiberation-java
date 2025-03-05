package com.example.service;

import org.mindrot.jbcrypt.BCrypt;

import com.example.dto.LoginDTO;
import com.example.dto.SignupDTO;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.google.gson.Gson;

public class UserService {

    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
 // 회원가입: 기존 사용자 확인 후 신규 사용자 저장
public boolean signup(SignupDTO signupDTO) {
    // signupDTO를 JSON 문자열로 출력
    System.out.println("Received SignupDTO: " + new Gson().toJson(signupDTO));
    // 이미 존재하는 사용자라면 가입 불가
    if (userRepository.findByEmail(signupDTO.getEmail()) != null) {
        return false;
    }
    // 실제 운영 시 비밀번호는 해시처리 필요
    User newUser = new User();
    newUser.setEmail(signupDTO.getEmail());
    newUser.setPassword(signupDTO.getPassword());
    newUser.setName(signupDTO.getName());
    return userRepository.save(newUser);
}
    
    /**
     * 로그인 처리: 입력된 이메일로 사용자를 조회하고,
     * 평문 비밀번호와 암호화된 비밀번호를 비교합니다.
     * 로그인 성공 시 User 객체를 반환하고, 실패하면 예외를 던집니다.
     */
    public User login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if (user == null) {
            throw new RuntimeException("해당 사용자가 존재하지 않습니다.");
        }
        if (BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
            System.out.println("로그인 성공!");
            return user;
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
