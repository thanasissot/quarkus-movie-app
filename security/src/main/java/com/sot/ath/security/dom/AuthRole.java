package com.sot.ath.security.dom;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AuthRole {
    @Id
    @GeneratedValue
    private Long authRoleId;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public enum RoleType {
        ADMIN, USER, SUPERADMIN
    }

}