package com.sot.at.rest.mapper;

import com.sot.at.rest.dom.AuthUser;
import com.sot.at.rest.dto.AuthUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = MoviesUserHasViewedMapper.class)
public interface AuthUserMapper {

    MoviesUserHasViewedMapper moviesUserHasViewedMapper = Mappers.getMapper(MoviesUserHasViewedMapper.class);

    @Mapping(target = "authUser.viewedMovies.authUserId", ignore = true)
    AuthUserDto toDTO(AuthUser authUser);

    AuthUser toEntity(AuthUserDto authUserDTO);

}
