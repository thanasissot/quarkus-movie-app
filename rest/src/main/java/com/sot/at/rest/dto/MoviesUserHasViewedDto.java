package com.sot.at.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoviesUserHasViewedDto {
    private Long id;
    private Long authUserId;
    private Long movieId;
}
