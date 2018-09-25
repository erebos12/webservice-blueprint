package com.alex.ws;

import com.alex.microservice.config.MicroserviceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MicroserviceConfig.class)
public class PortfolioWsMain {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioWsMain.class);

    public static void main(String[] args) {
        logger.info("Starting PortfolioWsMain ...");
        SpringApplication app = new SpringApplication(PortfolioWsMain.class);
        app.run();
        logger.info("Started PortfolioWsMain successfully.");
    }
}
