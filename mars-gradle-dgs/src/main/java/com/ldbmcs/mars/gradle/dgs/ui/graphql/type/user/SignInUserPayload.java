package com.ldbmcs.mars.gradle.dgs.ui.graphql.type.user;

import com.ldbmcs.mars.gradle.dgs.core.domain.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInUserPayload {
    private User user;
    private String token;
}
