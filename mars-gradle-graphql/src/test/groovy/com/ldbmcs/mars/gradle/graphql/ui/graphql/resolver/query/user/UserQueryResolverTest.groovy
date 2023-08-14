package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.query.user

import com.ldbmcs.mars.gradle.graphql.core.domain.user.models.User
import com.ldbmcs.mars.gradle.graphql.core.domain.user.repository.UserMapper
import com.ldbmcs.mars.gradle.graphql.infra.context.CurrentContext
import spock.lang.Specification

class UserQueryResolverTest extends Specification {
    def userMapper = Mock(UserMapper)
    def resolver = new UserQueryResolver(userMapper: userMapper)

    def "GetUsers"() {
        given:
        def users = [Mock(User)]

        when:
        def result = resolver.getUsers()

        then:
        1 * userMapper.selectList(null) >> users
        result == users
    }

    def "Me"() {
        given:
        def user = new User()
        CurrentContext.setUser(user)

        when:
        def result = resolver.me()

        then:
        result == user
    }
}

