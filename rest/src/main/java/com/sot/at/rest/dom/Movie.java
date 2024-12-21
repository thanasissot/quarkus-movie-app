package com.sot.at.rest.dom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String releaseYear;

    @JsonIgnore
    @ManyToMany(mappedBy = "movies", targetEntity = Actor.class)
    private Set<Actor> actors = new HashSet<>();
}
