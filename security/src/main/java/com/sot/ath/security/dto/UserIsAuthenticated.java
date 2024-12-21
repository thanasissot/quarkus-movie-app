package com.sot.ath.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserIsAuthenticated {
    public boolean authenticated;
}
