package com.example.controller;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.service.UserService;
import com.google.gson.Gson;

import static spark.Spark.post;

public class UserController {

    private final UserService userService;
    private final Gson gson;
    
    public UserController(UserService userService) {
        this.userService = userService;
        this.gson = new Gson();
        setupRoutes();
    }
    
    private void setupRoutes() {
        // 회원가입 엔드포인트: POST /api/users/register
        post("/api/users/register", (req, res) -> {
            UserDto userDto = gson.fromJson(req.body(), UserDto.class);
            try {
                userService.register(userDto.getUsername(), userDto.getEmail(), userDto.getPassword());
                res.status(200);
                return "Registration successful";
            } catch (RuntimeException e) {
                res.status(400);
                return e.getMessage();
            }
        });
        
        // 로그인 엔드포인트: POST /api/users/login
        post("/api/users/login", (req, res) -> {
            LoginDto loginDto = gson.fromJson(req.body(), LoginDto.class);
            try {
                return gson.toJson(userService.login(loginDto.getUsername(), loginDto.getPassword()));
            } catch (RuntimeException e) {
                res.status(400);
                return e.getMessage();
            }
        });
    }
}
