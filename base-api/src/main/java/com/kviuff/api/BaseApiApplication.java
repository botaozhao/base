package com.kviuff.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"com.kviuff"})
@EnableTransactionManagement
@MapperScan(basePackages = "com.kviuff.mapper")
public class BaseApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApiApplication.class, args);
    }
}
