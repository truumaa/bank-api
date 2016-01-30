package com.pocopay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Transactional(rollbackFor = Exception.class)
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        logger.debug("---algus---");
        SpringApplication.run(Application.class, args);
    }
}
