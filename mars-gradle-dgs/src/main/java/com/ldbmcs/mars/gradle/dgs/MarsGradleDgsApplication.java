package com.ldbmcs.mars.gradle.dgs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ldbmcs.mars.gradle.dgs.core.domain.*.repository")
public class MarsGradleDgsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarsGradleDgsApplication.class, args);
    }

}
