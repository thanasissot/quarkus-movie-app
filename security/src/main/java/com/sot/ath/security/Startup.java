package com.sot.ath.security;

import com.sot.ath.security.dom.AuthRole;
import com.sot.ath.security.dom.AuthUser;
import com.sot.ath.security.repo.AuthRepository;
import com.sot.ath.security.repo.UserRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.HashSet;

import static com.sot.ath.security.dom.AuthRole.RoleName.*;

@Singleton
public class Startup {

    @Inject
    AuthRepository authRepository;

    @Inject
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        AuthUser authUserSuperAdmin = new AuthUser();
        authUserSuperAdmin.setUsername("superAdmin");
        authUserSuperAdmin.setPassword("superAdmin");

        AuthUser authUserAdmin = new AuthUser();
        authUserAdmin.setUsername("admin");
        authUserAdmin.setPassword("admin");

        AuthUser authUserUser = new AuthUser();
        authUserUser.setUsername("user");
        authUserUser.setPassword("user");

        // reset and load all test users
        AuthRole authRoleSuperAdmin = new AuthRole();
        authRoleSuperAdmin.setRoleName(SUPERADMIN);
        authRepository.createAuthRole(authRoleSuperAdmin);

        AuthRole authRoleAdmin = new AuthRole();
        authRoleAdmin.setRoleName(ADMIN);
        authRepository.createAuthRole(authRoleAdmin);

        AuthRole authRoleUser = new AuthRole();
        authRoleUser.setRoleName(USER);
        authRepository.createAuthRole(authRoleUser);

        HashSet<AuthRole> authRolesSuperAdmin = new HashSet<>();
        authRolesSuperAdmin.add(authRoleSuperAdmin);
        authRolesSuperAdmin.add(authRoleAdmin);
        authRolesSuperAdmin.add(authRoleUser);
        authUserSuperAdmin.setAuthRoles(authRolesSuperAdmin);
        userRepository.createAuthUser(authUserSuperAdmin);

        HashSet<AuthRole> authRolesAdmin = new HashSet<>();
        authRolesAdmin.add(authRoleAdmin);
        authRolesAdmin.add(authRoleUser);
        authUserAdmin.setAuthRoles(authRolesAdmin);
        userRepository.createAuthUser(authUserAdmin);

        HashSet<AuthRole> authRolesUser = new HashSet<>();
        authRolesUser.add(authRoleUser);
        authUserUser.setAuthRoles(authRolesUser);
        userRepository.createAuthUser(authUserUser);


    }
}
