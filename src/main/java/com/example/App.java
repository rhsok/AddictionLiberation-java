package com.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.example.controller.AuthController;
import com.example.repository.UserRepository;
import com.example.routes.AuthRoutes;
import com.example.service.UserService;

import io.javalin.Javalin;

public class App 
{
    public static void main( String[] args )
    {

        var app = Javalin.create(/*config*/);

        app.before(ctx -> {
            System.out.println("Request received: " + ctx.method() + " " + ctx.path());
            if (ctx.contentType() == null) {
                ctx.contentType("application/json");
            }
        });

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AddictionLiberationPU");
        EntityManager em = emf.createEntityManager();

        // Repository, Service, Controller 계층 초기화
        UserRepository userRepository = new UserRepository(em);
        UserService userService = new UserService(userRepository);
        new AuthController(userService);  

        // 라우트 등록 (별도 클래스로 분리)
        AuthRoutes.register(app);

        app
        .get("/", ctx -> ctx.result("Hello World"))
        .start(7070);


        // 애플리케이션 종료 시 EntityManager 및 팩토리 정리
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            em.close();
            emf.close();
        }));
        
    }
}
