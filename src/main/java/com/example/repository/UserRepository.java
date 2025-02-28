package com.example.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.example.entity.User;

public class UserRepository {

    private final EntityManager em;
    
    public UserRepository(EntityManager em) {
        this.em = em;
    }
    
    // 사용자 저장 (Create)
    public void save(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }
    
    // 사용자명이나 이메일로 사용자 조회 (Read)
    public List<User> findByUsernameOrEmail(String username, String email) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username OR u.email = :email", User.class);
        query.setParameter("username", username);
        query.setParameter("email", email);
        return query.getResultList();
    }
    
    // 사용자명으로 단일 사용자 조회 (Read)
    public User findByUsername(String username) {
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
