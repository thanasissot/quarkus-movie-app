package com.sot.at.rest.repo;

import com.sot.at.rest.dom.AppUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class AppUserRepository {
    @PersistenceContext
    EntityManager em;

    public AppUser findByUsername(String username) {
        return em.createQuery("SELECT u FROM AppUser u WHERE u.username = :username", AppUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public void createAppUser(AppUser appUser) {
        em.persist(appUser);
    }

}