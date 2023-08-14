package com.ldbmcs.mars.gradle.graphql.ui.graphql.type.user;

import com.ldbmcs.mars.gradle.graphql.core.domain.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpUserPayload {
    private User user;
}
