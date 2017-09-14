package com.bisnode.bhc.configuration;

import com.bisnode.bhc.domain.Portfolio;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CfgParams {

    private static final String hibernateCfgFile = "hibernate.cfg.xml";
    private static final String h2TestDataFile = "bhc-data-h2.sql";

    public static List<Class<?>> getHibernateTables() {
        return Arrays.asList(Portfolio.class);
    }

    public static String getH2DataFile() throws MalformedURLException {
        return ResourceHelper.getResource(h2TestDataFile).getFile();
    }

    public static URL getHibernateCfgFile() throws MalformedURLException {
        return ResourceHelper.getResource(hibernateCfgFile);
    }
}
