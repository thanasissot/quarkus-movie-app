package com.sot.at.rest.resource;

import com.sot.at.rest.dom.AuthUser;
import com.sot.at.rest.repo.AppUserRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/user")
public class AppUserResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    AppUserRepository appUserRepository;

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public AuthUser getUser(@Context SecurityContext ctx) {
        return appUserRepository.findByUsername("user");
    }

    @GET
    @Path("/auth")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER"})
    public Response getData(@Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        // Fetch data for the user
        return Response.ok(username.concat("+OK")).build();
    }
}
