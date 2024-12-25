package com.sot.at.rest.mapper;

import com.sot.at.rest.dom.Actor;
import com.sot.at.rest.dto.ActorDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {MovieMapper.class})
public interface ActorMapper {

    MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);

    ActorDto toDTO(Actor actor);

    Actor toEntity(ActorDto actorDTO);

}
