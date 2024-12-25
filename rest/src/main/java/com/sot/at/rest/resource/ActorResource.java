package com.sot.at.rest.resource;

import com.sot.at.rest.dom.Actor;
import com.sot.at.rest.dom.Movie;
import com.sot.at.rest.dto.ActorAssignMoviesDto;
import com.sot.at.rest.dto.ActorDto;
import com.sot.at.rest.mapper.ActorMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Path("/actors")
public class ActorResource {

    @Inject
    ActorMapper actorMapper;

    @GET
    @Path("all")
    public List<ActorDto> getAllActors() {
        List<Actor> actors = Actor.findAll().list();
        return actors.stream()
                .map(actorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PUT
    @Transactional
    public Response createActor(ActorDto actorDto) {
        Actor actor = new Actor();
        actor.setFirstName(actorDto.getFirstName());
        actor.setLastName(actorDto.getLastName());
        actor.persist();

        return Response.ok(actor).build();
    }

    @PUT
    @Path("assign/movies")
    @Transactional
    public Response assignMoviesToActor(ActorAssignMoviesDto actorAssignMoviesDto) {
        Actor actor = Actor.findById(actorAssignMoviesDto.getActorId());
        List<Movie> movies = Movie.find("id IN ?1", actorAssignMoviesDto.getMovieIds()).list();

        actor.setMovies(new HashSet<>(movies));
        actor.persist();

        return Response.ok(actor).build();
    }

    @GET
    @Path("movie/{movieId}")
    public List<Actor> getAllActorsByMovieId(@PathParam("movieId") long movieId) {
        List<Actor> actors = Actor
                .find("SELECT a FROM Actor a join a.movies m where m.id = ?1", movieId)
                .list();
        return actors;
    }

}
