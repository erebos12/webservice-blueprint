package com.bisnode.bhc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CfgParams {

    @Value("${bhcws.mode:#{test}}")
    public String mode;
}
