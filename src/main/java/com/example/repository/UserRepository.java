package com.example.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.example.entity.User;

public class UserRepository {

    private final EntityManager em;
    
    public UserRepository(EntityManager em) {
        this.em = em;
    }
    
    // 회원가입 (Create)
    public boolean save(User user) {
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    // 이메일로 사용자 조회 (Read)
    public User findByEmail(String email) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        List<User> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    

}
