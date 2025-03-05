package com.example.controller;

import com.example.dto.LoginDTO;
import com.example.dto.SignupDTO;
import com.example.entity.User;
import com.example.service.UserService;
import com.google.gson.Gson;

import io.javalin.http.Context;

public class AuthController {
    private static UserService userService;
    private static final Gson gson = new Gson();

    // 생성자에서 UserService 주입 (싱글톤 형태로 사용 가능)
    public AuthController(UserService userService) {
        AuthController.userService = userService;
    }

    // 회원가입 처리
    public static void signup(Context ctx) {
        try {
             // 요청 관련 정보 로그 찍기
            System.out.println("Received request: " + ctx.method() + " " + ctx.path());
            System.out.println("Request body: " + ctx.body());

            SignupDTO dto = gson.fromJson(ctx.body(), SignupDTO.class);

            // dto 내용도 로그로 찍어보기 (toString() 오버라이드 되어있다면)
            System.out.println("Parsed SignupDTO: " + dto);

            boolean result = userService.signup(dto);
            if (result) {
                ctx.status(201).result("회원가입 성공");
            } else {
                ctx.status(400).result("회원가입 실패: 이미 등록된 사용자일 수 있습니다.");
            }
        } catch (Exception e) {
            ctx.status(500).result("서버 에러: " + e.getMessage());
        }
    }

    // 로그인 처리
    public static void login(Context ctx) {
        try {
            LoginDTO dto = gson.fromJson(ctx.body(), LoginDTO.class);
            User user = userService.login(dto);
            if (user != null) {
                ctx.status(200).result("로그인 성공");
            } else {
                ctx.status(401).result("로그인 실패: 이메일 또는 비밀번호가 올바르지 않습니다.");
            }
        } catch (Exception e) {
            ctx.status(500).result("서버 에러: " + e.getMessage());
        }
    }
}
