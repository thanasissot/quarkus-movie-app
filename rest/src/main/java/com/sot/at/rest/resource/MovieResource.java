package com.sot.at.rest.resource;

import com.sot.at.rest.dom.Movie;
import com.sot.at.rest.dto.MovieDto;
import com.sot.at.rest.mapper.MovieMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;
import java.util.stream.Collectors;

@Path("/movies")
public class MovieResource {

    @Inject
    MovieMapper movieMapper;

//    @GET
//    @Path("all")
//    public List<Movie> getAllMovies() {
//        List<Movie> movies = Movie.findAll().list();
//        return movies;
//    }

    @GET
    @Path("all")
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = Movie.findAll().list();
        return movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
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
