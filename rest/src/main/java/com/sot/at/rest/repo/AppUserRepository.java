package com.sot.at.rest.repo;

import com.sot.at.rest.dom.AuthUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class AppUserRepository {
    @PersistenceContext
    EntityManager em;

    public AuthUser findByUsername(String username) {
        return em.createQuery("SELECT u FROM AuthUser u WHERE u.username = :username", AuthUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public void createAppUser(AuthUser authUser) {
        em.persist(authUser);
    }

}