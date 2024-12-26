package com.sot.at.rest.resource;

import com.sot.at.rest.dom.AuthUser;
import com.sot.at.rest.dom.Movie;
import com.sot.at.rest.dom.MoviesUserHasViewed;
import com.sot.at.rest.dto.AddViewedMovieDto;
import com.sot.at.rest.dto.MoviesUserHasViewedDto;
import com.sot.at.rest.mapper.MoviesUserHasViewedMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/movies-viewed")
public class MoviesUserHasViewedResource {

    @Inject
    MoviesUserHasViewedMapper moviesUserHasViewedMapper;

    @GET
    @Path("all")
    public List<MoviesUserHasViewedDto> getAllMoviesViewedByUser() {
        List<MoviesUserHasViewed> moviesUserHasViewed = MoviesUserHasViewed.findAll().list();
        return moviesUserHasViewed.stream()
                .map(moviesUserHasViewedMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PUT
    @Transactional
    public Response addViewedMovie(AddViewedMovieDto addViewedMovieDto) {
        AuthUser authUser = AuthUser.findById(addViewedMovieDto.getActorId());
        Movie movie = Movie.findById(addViewedMovieDto.getMovieId());

        MoviesUserHasViewed moviesUserHasViewed = new MoviesUserHasViewed();
        moviesUserHasViewed.setAuthUser(authUser);
        moviesUserHasViewed.setMovie(movie);
        moviesUserHasViewed.setWatchList(addViewedMovieDto.isWatchlist());
        moviesUserHasViewed.setViewed(addViewedMovieDto.isViewed());
        moviesUserHasViewed.persist();

        return Response.ok().build();

    }

}
