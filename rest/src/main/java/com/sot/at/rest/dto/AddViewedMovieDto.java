package com.sot.at.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddViewedMovieDto {
    private Long actorId;
    private Long movieId;
    private boolean viewed;
    private boolean watchlist;
}
