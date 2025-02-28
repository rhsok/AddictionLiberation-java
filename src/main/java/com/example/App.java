package com.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.example.controller.UserController;
import com.example.repository.UserRepository;
import com.example.service.UserService;

import static spark.Spark.get;
import static spark.Spark.port;

public class App 
{
    public static void main( String[] args )
    {
         // Spark Java 서버 포트 설정 (기본 4567)
        port(4567);

        // 헬스체크 엔드포인트 등록 (추가)
        get("/ping", (req, res) -> "Server is up and running!");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AddictionLiberationPU");
        EntityManager em = emf.createEntityManager();

        // Repository, Service, Controller 계층 초기화
        UserRepository userRepository = new UserRepository(em);
        UserService userService = new UserService(userRepository);
        new UserController(userService);  

        // 애플리케이션 종료 시 EntityManager 및 팩토리 정리
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            em.close();
            emf.close();
        }));
        
    }
}
