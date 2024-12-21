package com.sot.at.rest.client;

import com.sot.at.rest.dto.UserIsAuthenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("secured/loggedIn")
@RegisterRestClient(configKey = "security-api")
public interface SecurityRestClient {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UserIsAuthenticated getUserIsAuthenticated(@HeaderParam("Authentication") String authHeader);
}
