package com.bisnode.bhc.infrastructure;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class CfgParams {

    public static final String configFolder = "config";
    public static final String hibernateCfgFile = "hibernate.cfg.xml";
    public static final String serverCfgFile = "config.json";
    public static final String h2TestDataFile = "bhc-data-h2.sql";
    public static final String version = "1.1";

    public static String getH2DataFile() throws MalformedURLException {
        URL fileUrl = Paths.get(configFolder, h2TestDataFile).toUri().toURL();
        return fileUrl.getFile();
    }

    public static URL getHibernateCfgFile() throws MalformedURLException {
        return Paths.get(CfgParams.configFolder, hibernateCfgFile).toUri().toURL();
    }
}
