package com.codef.api.dto;

import static com.google.common.base.Preconditions.checkNotNull;

public class JwtAuthentication {

    public final Long userId;


    public JwtAuthentication(Long userId ) {
        checkNotNull(userId, "userId must be provided.");

        this.userId = userId;
    }

}