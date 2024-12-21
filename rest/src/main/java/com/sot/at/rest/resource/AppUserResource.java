package com.sot.at.rest.resource;

import com.sot.at.rest.dom.AppUser;
import com.sot.at.rest.repo.AppUserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/user")
public class AppUserResource {

    @Inject
    AppUserRepository appUserRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AppUser getUser() {
        return appUserRepository.findByUsername("user");
    }
}
