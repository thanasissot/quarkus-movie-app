package com.sot.ath.security.repo;

import com.sot.ath.security.dom.AuthUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class UserRepository {
    @PersistenceContext
    EntityManager em;

    public AuthUser findByUsername(String username) {
        return em.createQuery("SELECT u FROM AuthUser u JOIN FETCH u.authRoles WHERE u.username = :username", AuthUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public void createAuthUser(AuthUser authUser) {
        em.persist(authUser);
    }




}
