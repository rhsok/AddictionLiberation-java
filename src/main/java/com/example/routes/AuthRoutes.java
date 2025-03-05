/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.routes;

import com.example.controller.AuthController;

import io.javalin.Javalin;

public class AuthRoutes {
  public static void register(Javalin app) {
          app.post("/signup", AuthController::signup);
          app.post("/login", AuthController::login);
      }
}
