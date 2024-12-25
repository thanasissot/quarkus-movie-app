package com.sot.at.rest.resource;

import com.sot.at.rest.dom.MoviesUserHasViewed;
import com.sot.at.rest.dto.MoviesUserHasViewedDto;
import com.sot.at.rest.mapper.MoviesUserHasViewedMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

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

}
