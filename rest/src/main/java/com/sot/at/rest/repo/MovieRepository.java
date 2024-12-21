package com.sot.at.rest.repo;

import com.sot.at.rest.dom.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class MovieRepository {

    @PersistenceContext
    EntityManager em;

    public Movie findByTitle(String title) {
        return em.createQuery("SELECT u FROM Movie u WHERE u.title = :title", Movie.class)
                .setParameter("title", title)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public void createMovie(Movie movie) {
        em.persist(movie);
    }

}