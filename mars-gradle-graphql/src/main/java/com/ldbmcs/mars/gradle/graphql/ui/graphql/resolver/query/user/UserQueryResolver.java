package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.query.user;

import com.ldbmcs.mars.gradle.graphql.core.domain.user.models.User;
import com.ldbmcs.mars.gradle.graphql.core.domain.user.repository.UserMapper;
import com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.query.BaseSecuredQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserQueryResolver extends BaseSecuredQueryResolver {
    @Autowired
    private UserMapper userMapper;

    public List<User> getUsers() {
        return userMapper.selectList(null);
    }

    public User me() {
        return getCurrentUser();
    }
}
