package com.example.dto;

public class LoginDTO {
    private String email;
    private String password;

    // 기본 생성자
    public LoginDTO() {}

    // 모든 필드를 포함한 생성자
    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter, Setter
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
