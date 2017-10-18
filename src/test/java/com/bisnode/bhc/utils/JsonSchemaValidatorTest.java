package com.bisnode.bhc.utils;

import com.bisnode.bhc.domain.exception.InvalidPortfolioMessageException;
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

    private JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator();
    private ObjectMapper mapper = new ObjectMapper();
    private IncomingPortfolioJsonBuilder jsonBuilder = new IncomingPortfolioJsonBuilder();

    @Before
    public void setup() throws IOException {
        jsonSchemaValidator.loadJsonSchema(jsonSchemaValidator.jsonFile2JsonNode("schema.json"));
    }

    @Test(expected = InvalidPortfolioMessageException.class)
    public void whenSystemIdIsInvalid_thenExpect_SuccessIsFalse() throws IOException, ProcessingException, InvalidPortfolioMessageException {
        String json = jsonBuilder.build()
                .withSystemId("InvalidSystemId")
                .withCompany("43756823", "12321432", "SE", "Small")
                .asJson();
        Assert.assertEquals(false, jsonSchemaValidator.validate(mapper.readTree(json)).isSuccess());
    }

    @Test
    public void whenPortfolioIsValid_thenExpect_SuccessIsTrue() throws IOException, ProcessingException, InvalidPortfolioMessageException {
        String json = jsonBuilder.build()
                .withSystemId("PBC")
                .withCompany("43257", "54309888", "DE", "Large")
                .withCompany("44561", "54435", "US", "Medium")
                .withCompany("43756823", "12321432", "SE", "Small")
                .asJson();
        Assert.assertEquals(true, jsonSchemaValidator.validate(mapper.readTree(json)).isSuccess());
    }
}