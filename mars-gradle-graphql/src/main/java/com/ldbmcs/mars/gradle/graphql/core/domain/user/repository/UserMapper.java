package com.ldbmcs.mars.gradle.graphql.core.domain.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldbmcs.mars.gradle.graphql.core.domain.user.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
