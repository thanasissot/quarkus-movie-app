package com.sot.at.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDto {
    private Long id;
    private String title;
    private String releaseYear;
}
