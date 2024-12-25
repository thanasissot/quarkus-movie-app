package com.sot.at.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ActorAssignMoviesDto {
    private long actorId;
    private Set<Long> movieIds;
}
