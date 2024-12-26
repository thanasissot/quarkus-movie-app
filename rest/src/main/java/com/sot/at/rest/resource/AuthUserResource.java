package com.sot.at.rest.resource;

import com.sot.at.rest.dom.AuthUser;
import com.sot.at.rest.dto.AuthUserDto;
import com.sot.at.rest.mapper.AuthUserMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.stream.Collectors;

@Path("/user")
public class AuthUserResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    AuthUserMapper authUserMapper;

    @GET
    @Path("all")
    public List<AuthUserDto> getAllAuthUsers() {
        List<AuthUser> users = AuthUser.findAll().list();
        return users.stream()
                .map(authUserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @POST
    public AuthUserDto getAuthUserByUserName(AuthUserDto authUserDto) {
        AuthUser authUser = AuthUser.find("username", authUserDto.getUsername()).firstResult();
        return authUserMapper.toDTO(authUser);
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
