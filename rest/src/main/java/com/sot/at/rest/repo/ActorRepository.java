package com.sot.at.rest.repo;

import com.sot.at.rest.dom.Actor;
import com.sot.at.rest.dom.AppUser;
import com.sot.at.rest.dom.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class ActorRepository {

    @PersistenceContext
    EntityManager em;

    public void createActor(Actor actor) {
        em.persist(actor);
    }

    public Actor findByFirstName(String firstName) {
        return em.createQuery("SELECT u FROM Actor u WHERE u.firstName = :firstName", Actor.class)
                .setParameter("firstName", firstName)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<Actor> findAll() {
       return em.createQuery("SELECT a FROM Actor a LEFT JOIN FETCH a.movies", Actor.class).getResultList();
    }

}
