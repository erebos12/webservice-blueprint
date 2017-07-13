package com.bisnode.bhc.infrastructure;

import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class TestH2Initializer {

	private static final Logger logger = LoggerFactory.getLogger(TestH2Initializer.class);
	
    private TestH2Initializer() {
        // no instances
    }

    public static void initializeH2(String db_structure_file) throws SQLException, RuntimeException {
    	logger.info("initializeH2 - " + db_structure_file);
        // Keep the content of an in-memory database as long as the virtual machine is alive
        try (InputStream is = new FileInputStream(db_structure_file)) {        	
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
