package com.sot.at.rest.mapper;

import com.sot.at.rest.dom.MoviesUserHasViewed;
import com.sot.at.rest.dto.MoviesUserHasViewedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface MoviesUserHasViewedMapper {

    @Mapping(target = "authUserId", source = "moviesUserHasViewed.authUser.id")
    @Mapping(target = "movieId", source = "moviesUserHasViewed.movie.id")
    MoviesUserHasViewedDto toDTO(MoviesUserHasViewed moviesUserHasViewed);

    MoviesUserHasViewed toEntity(MoviesUserHasViewedDto moviesUserHasViewedDto);

}
