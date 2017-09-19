package com.bisnode.bhc.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.util.AsJson;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public class JsonSchemaValidatorTest {

    JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator();
    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() throws IOException {
        jsonSchemaValidator.loadJsonSchema(jsonFile2JsonNode("schema.json"));
    }

    @Test
    public void test_schemaValidator() throws IOException, ProcessingException {
        Assert.assertEquals(false, jsonSchemaValidator.validate(jsonFile2JsonNode("incoming_portfolio_invalid_systemId.json")).isSuccess());
    }

    @Test
    public void test_schemaValidator02() throws IOException, ProcessingException {
        Assert.assertEquals(true, jsonSchemaValidator.validate(jsonFile2JsonNode("incoming_portfolio01.json")).isSuccess());
    }

    @Test
    public void validateBySchema() throws IOException, ProcessingException {
        ProcessingReport report = jsonSchemaValidator.validate(jsonFile2JsonNode("incoming_portfolio_invalid_systemId.json"));
        final boolean success = report.isSuccess();
        Assert.assertEquals(false, success);
        final JsonNode reportAsJson = ((AsJson) report).asJson();
        System.out.println(reportAsJson);
    }

    private JsonNode jsonFile2JsonNode(String file) throws IOException {
        URL url = Resources.getResource(file);
        String s =  Resources.toString(url, Charsets.UTF_8);
        return mapper.readTree(s);
    }
}