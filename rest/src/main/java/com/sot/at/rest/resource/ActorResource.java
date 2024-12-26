package com.sot.at.rest.resource;

import com.sot.at.rest.dom.Actor;
import com.sot.at.rest.dom.Movie;
import com.sot.at.rest.dto.ActorAssignMoviesDto;
import com.sot.at.rest.dto.ActorDto;
import com.sot.at.rest.mapper.ActorMapper;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Path("/actors")
public class ActorResource {

    @Inject
    ActorMapper actorMapper;

    @GET
    public List<ActorDto> getAllActors(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sortDir") @DefaultValue("ASC") String sortDir,
            @QueryParam("sortField") @DefaultValue("firstName") String sortField
    ) {
        List<Actor> actors = Actor
                .findAll(Sort.by(sortField).direction(sortDir.equals("ASC") ? Sort.Direction.Ascending : Sort.Direction.Descending))
                .page(Page.of(page, size)).list();

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
    @Path("by-movie/{movieId}")
    public List<ActorDto> getAllActorsByMovieId(@PathParam("movieId") long movieId) {
        List<Actor> actors = Actor
                .find("SELECT a FROM Actor a join a.movies m where m.id = ?1", movieId)
                .list();
        return actors.stream()
                .map(actorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GET
    @Path("available-for/{movieId}")
    public List<ActorDto> getAllAvailableActors(@PathParam("movieId") long movieId) {
        List<Actor> actors = Actor
                .find("SELECT a FROM Actor a WHERE a.id NOT IN (SELECT ma.id FROM Movie m JOIN m.actors ma WHERE m.id = ?1)", movieId)
                .list();
        return actors.stream()
                .map(actor ->  {
                    ActorDto actorDto = actorMapper.toDTO(actor);
                    actorDto.setMovies(null);
                    return actorDto;
                })
                .collect(Collectors.toList());
    }


}
