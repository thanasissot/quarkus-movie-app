package com.sot.at.rest.mapper;

import com.sot.at.rest.dom.Movie;
import com.sot.at.rest.dto.MovieDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface MovieMapper {
    MovieDto toDTO(Movie movie);

    Movie toEntity(MovieDto movieDTO);

}
