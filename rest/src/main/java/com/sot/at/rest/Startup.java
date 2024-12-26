package com.sot.at.rest;

import com.sot.at.rest.dom.Actor;
import com.sot.at.rest.dom.AuthUser;
import com.sot.at.rest.dom.Movie;
import com.sot.at.rest.dom.MoviesUserHasViewed;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.HashSet;


@Singleton
public class Startup {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        AuthUser authUser = new AuthUser();
        authUser.setUsername("user");
        authUser.persist();

        Movie movie = new Movie();
        movie.setTitle("Movie 1");
        movie.setReleaseYear("1987");

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setReleaseYear("1988");

        Movie movie3 = new Movie();
        movie3.setTitle("Movie 3");
        movie3.setReleaseYear("1989");

        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");

        // Persist the movies first
        movie.persist();
        movie2.persist();
        movie3.persist();

        // Associate the movies with the actor
        HashSet<Movie> movies = new HashSet<>();
        movies.add(movie);
        movies.add(movie2);
        actor.setMovies(movies);

        // Persist the actor, which will update the join table automatically
        actor.persist();

        // Now, create another actor and associate them with the movies
        Actor actor1 = new Actor();
        actor1.setFirstName("John 1");
        actor1.setLastName("Doe 1");

        HashSet<Movie> movies1 = new HashSet<>();
        movies1.add(movie);
        actor1.setMovies(movies1);

        // Persist the second actor
        actor1.persist();

        MoviesUserHasViewed moviesUserHasViewed = new MoviesUserHasViewed();
        moviesUserHasViewed.setAuthUser(authUser);
        moviesUserHasViewed.setMovie(movie);
        moviesUserHasViewed.setViewed(true);
        moviesUserHasViewed.persist();

        MoviesUserHasViewed moviesUserHasViewed2 = new MoviesUserHasViewed();
        moviesUserHasViewed2.setAuthUser(authUser);
        moviesUserHasViewed2.setMovie(movie2);
        moviesUserHasViewed2.setWatchList(true);
        moviesUserHasViewed2.persist();

    }
}
