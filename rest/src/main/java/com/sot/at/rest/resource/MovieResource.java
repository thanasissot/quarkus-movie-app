package com.sot.at.rest.resource;

import com.sot.at.rest.dom.Actor;
import com.sot.at.rest.dom.Movie;
import com.sot.at.rest.dto.MovieAssignActorsDto;
import com.sot.at.rest.dto.MovieDto;
import com.sot.at.rest.mapper.MovieMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/movies")
public class MovieResource {

    @Inject
    MovieMapper movieMapper;

    @GET
    @Path("all")
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = Movie.findAll().list();
        return movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PUT
    @Transactional
    public Response createMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.persist();

        return Response.ok(movie).build();
    }

    @PUT
    @Path("assign/actors")
    @Transactional
    public Response assignActorsToMovies(MovieAssignActorsDto movieAssignActorsDto) {
        Movie movie = Movie.findById(movieAssignActorsDto.getMovieId());
        List<Actor> actors = Actor.find("id IN ?1", movieAssignActorsDto.getActorIds()).list();

        for (Actor actor : actors) {
            Set<Movie> movies = actor.getMovies();
            movies.add(movie);
        }

        Actor.persist(actors);

        return Response.ok(movie).build();
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
