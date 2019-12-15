package com.echo.miaoshaship;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.echo.miaoshaship"})
@MapperScan("com.echo.miaoshaship.dao")
public class MiaoshashipApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaoshashipApplication.class, args);
    }

}
