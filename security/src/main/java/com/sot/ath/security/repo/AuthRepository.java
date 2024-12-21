package com.sot.ath.security.repo;

import com.sot.ath.security.dom.AuthRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class AuthRepository {
    @PersistenceContext
    EntityManager em;

    public void createAuthRole(AuthRole authRole) {
        em.persist(authRole);
    }
}
