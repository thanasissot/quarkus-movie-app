package com.sot.at.rest.resource;

import com.sot.at.rest.dom.Actor;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/actors")
public class ActorResource {

    @GET
    @Path("all")
    public List<Actor> getAllActors() {
        List<Actor> actors = Actor.findAll().list();
        return actors;
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
