package com.bisnode.bhc;

import com.bisnode.microservice.config.MicroserviceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MicroserviceConfig.class)
public class BhcWsMain {

    private static final Logger logger = LoggerFactory.getLogger(BhcWsMain.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BhcWsMain.class);
        app.run();
        logger.info("Started BhcWsMain successfully.");
    }
}
