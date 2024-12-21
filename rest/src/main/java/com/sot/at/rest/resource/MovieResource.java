package com.sot.at.rest.resource;

import com.sot.at.rest.dom.Actor;
import com.sot.at.rest.dom.Movie;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/movies")
public class MovieResource {

    @GET
    @Path("all")
    public List<Movie> getAllMovies() {
        List<Movie> movies = Movie.findAll().list();
        return movies;
    }

    @GET
    @Path("actor/{actorId}")
    public List<Movie> getAllMoviesByActorId(@PathParam("actorId") long actorId) {
        List<Movie> movies = Movie
                .find("SELECT a FROM Movie a JOIN a.actors m where m.id = ?1", actorId)
                .list();
        return movies;
    }
}
