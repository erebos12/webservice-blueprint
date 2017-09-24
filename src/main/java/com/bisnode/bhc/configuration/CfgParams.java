package com.bisnode.bhc.configuration;

import com.bisnode.bhc.utils.H2DbInitializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class CfgParams {

    @Value("${bhcws.mode:{test}}")
    public String mode;

    public String persistence_unit;

    public CfgParams() throws SQLException {
        if ("prod".equalsIgnoreCase(mode)) {
            persistence_unit = "portfolio_prod";
        } else {
            persistence_unit = "portfolio";
            H2DbInitializer.initializeH2();
        }
    }
}
