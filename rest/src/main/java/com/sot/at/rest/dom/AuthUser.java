package com.sot.at.rest.dom;

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
public class AuthUser extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    @OneToMany(mappedBy = "authUser", cascade = CascadeType.ALL)
    private Set<MoviesUserHasViewed> viewedMovies = new HashSet<>();

}
