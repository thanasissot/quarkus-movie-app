package com.sot.at.rest.dom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private long id;

    private String username;


}
