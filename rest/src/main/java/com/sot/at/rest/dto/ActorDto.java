package com.sot.at.rest.dto;

import com.sot.at.rest.dom.Movie;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class ActorDto {
    private String firstName;
    private String lastName;
    private Set<Movie> movies = new HashSet<>();
}
