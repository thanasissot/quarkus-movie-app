package com.sot.at.rest.resource;

import com.sot.at.rest.dom.Actor;
import com.sot.at.rest.dom.Movie;
import com.sot.at.rest.dto.MovieAssignActorsDto;
import com.sot.at.rest.dto.MovieDto;
import com.sot.at.rest.mapper.MovieMapper;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/movies")
public class MovieResource {

    @Inject
    MovieMapper movieMapper;

    @GET
    public List<MovieDto> getAllMovies(@QueryParam("page") @DefaultValue("0") int page,
                                       @QueryParam("size") @DefaultValue("10") int size,
                                       @QueryParam("sortDir") @DefaultValue("ASC") String sortDir,
                                       @QueryParam("sortField") @DefaultValue("title") String sortField) {
        List<Movie> movies = Movie
                .findAll(Sort.by(sortField).direction(sortDir.equals("ASC") ? Sort.Direction.Ascending : Sort.Direction.Descending))
                .page(Page.of(page, size)).list();
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
    @Path("by-actor/{actorId}")
    public List<MovieDto> getAllMoviesByActorId(@PathParam("actorId") long actorId) {
        List<Movie> movies = Movie
                .find("SELECT a FROM Movie a JOIN a.actors m where m.id = ?1", actorId)
                .list();
        return movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GET
    @Path("available-for/{actorId}")
    public List<MovieDto> getAllAvailableMovies(@PathParam("actorId") long actorId) {
        List<Movie> movies = Movie
                .find("SELECT a FROM Movie a WHERE a.id NOT IN (SELECT ma.id FROM Actor m JOIN m.movies ma WHERE m.id = ?1)", actorId)
                .list();
        return movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }
}
