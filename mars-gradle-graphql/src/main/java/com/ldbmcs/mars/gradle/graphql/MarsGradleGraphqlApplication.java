package com.ldbmcs.mars.gradle.graphql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ldbmcs.mars.gradle.graphql.core.domain.*.repository")
public class MarsGradleGraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarsGradleGraphqlApplication.class, args);
    }

}
