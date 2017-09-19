package com.bisnode.bhc.configuration;

import com.bisnode.bhc.domain.Portfolio;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class CfgParams {

    private static final String hibernateCfgFile = "hibernate.cfg.xml";
    private static final String h2TestDataFile = "bhc-data-h2.sql";

    public static List<Class<?>> getHibernateTables() {
        return Arrays.asList(Portfolio.class);
    }

    public static String getH2DataFile() throws IOException {
        return Resources.getResource(h2TestDataFile).getFile();
    }

    public static URL getHibernateCfgFile() throws MalformedURLException {
        return Resources.getResource(hibernateCfgFile);
    }
}
