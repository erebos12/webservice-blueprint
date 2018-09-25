package com.alex.ws.bdd;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Test driver class with configuration of cucumber to run Cucumber tests.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:build/reports/cucumber"},
        glue = "com.alex.bhc.bdd.stepdefinition",
        monochrome = true,
        features = "src/test/resources/features"
)
public class CucumberTestRunner {
    // executing this class will run all tests defined in feature files
}