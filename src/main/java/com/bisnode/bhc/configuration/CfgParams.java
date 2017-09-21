package com.bisnode.bhc.configuration;

import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.TableSelector;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CfgParams {

    private static final Logger logger = LoggerFactory.getLogger(CfgParams.class);
    private static final String hibernateCfgFileTest = "hibernate.cfg.xml";
    private static final String hibernateCfgFileProduction = "hibernate.cfg.prod.xml";
    private static final String h2TestDataFile = "bhc-data-h2.sql";

    @Value("${bhcws.mode}")
    private static String mode;

    public static List<Class<?>> getHibernateTables() {
        return Arrays.asList(Portfolio.class);
    }

    public static String getH2DataFile() throws IOException {
        return Resources.getResource(h2TestDataFile).getFile();
    }

    public static URL getHibernateCfgFile() throws MalformedURLException {
        if ("prod".equalsIgnoreCase(mode)) {
            logger.info("running with production mode");
            return Resources.getResource(hibernateCfgFileProduction);
        }
        return Resources.getResource(hibernateCfgFileTest);
    }
}
