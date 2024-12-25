package com.sot.at.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthUserDto {
    private long id;
    private String username;
    private Set<MoviesUserHasViewedDto> viewedMovies; // List of viewed movie IDs
}
