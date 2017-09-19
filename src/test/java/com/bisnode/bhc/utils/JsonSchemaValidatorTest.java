package com.bisnode.bhc.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.util.AsJson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class JsonSchemaValidatorTest {

    JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator();
    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() throws IOException {
        jsonSchemaValidator.loadJsonSchema(jsonSchemaValidator.jsonFile2JsonNode("schema.json"));
    }

    @Test(expected = InvalidPortfolioMessageException.class)
    public void test_schemaValidator() throws IOException, ProcessingException, InvalidPortfolioMessageException {
        Assert.assertEquals(false, jsonSchemaValidator.validate(jsonSchemaValidator.jsonFile2JsonNode("incoming_portfolio_invalid_systemId.json")).isSuccess());
    }

    @Test
    public void test_schemaValidator02() throws IOException, ProcessingException, InvalidPortfolioMessageException {
        Assert.assertEquals(true, jsonSchemaValidator.validate(jsonSchemaValidator.jsonFile2JsonNode("incoming_portfolio01.json")).isSuccess());
    }

    @Test(expected = InvalidPortfolioMessageException.class)
    public void validateBySchema() throws IOException, ProcessingException, InvalidPortfolioMessageException {
        ProcessingReport report = jsonSchemaValidator.validate(jsonSchemaValidator.jsonFile2JsonNode("incoming_portfolio_invalid_systemId.json"));
        final boolean success = report.isSuccess();
        Assert.assertEquals(false, success);
        final JsonNode reportAsJson = ((AsJson) report).asJson();
        System.out.println(reportAsJson);
    }
}