package com.bisnode.bhc.utils;

import com.google.common.io.Resources;
import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class H2DbInitializer {

	private static final Logger logger = LoggerFactory.getLogger(H2DbInitializer.class);
    private static final String h2TestDataFile = "bhc-data-h2.sql";
    private static final String h2CfgFile = Resources.getResource(h2TestDataFile).getFile();
	
    private H2DbInitializer() {
        // no instances
    }

    public static void initializeH2() throws SQLException, RuntimeException {
    	logger.info("initializeH2 - " + h2TestDataFile);
        // Keep the content of an in-memory database as long as the virtual machine is alive
        try (InputStream is = new FileInputStream(h2CfgFile)) {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;TRACE_LEVEL_FILE=4");
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            RunScript.execute(connection, reader);
            connection.close();
        } catch (IOException e) {
        	String errMsg = "Unable to initialize H2 DB";
        	logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        logger.info("initialize H2 successfully");
    }
}
