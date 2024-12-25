package com.sot.at.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class MovieAssignActorsDto {
    private Long movieId;
    private Set<Long> actorIds;
}
