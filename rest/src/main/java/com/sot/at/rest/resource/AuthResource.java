package com.sot.at.rest.resource;

import com.sot.at.rest.client.SecurityRestClient;
import com.sot.at.rest.dto.UserIsAuthenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestHeader;

@Path("/logged")
public class AuthResource {
    @Inject
    @RestClient
    SecurityRestClient restClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserIsAuthenticated getLoggedUser(
            @RestHeader("Authorization") String authHeader) {
        return restClient.getUserIsAuthenticated(authHeader);
    }
}
