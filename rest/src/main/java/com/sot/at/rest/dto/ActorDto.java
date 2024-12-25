package com.sot.at.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ActorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<MovieDto> movies; // Map movie IDs only
}
