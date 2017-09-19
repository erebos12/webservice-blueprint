package com.bisnode.bhc.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.util.AsJson;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

public class JsonSchemaValidator {

    private JsonNode schemaNode;
    private JsonValidator validator = JsonSchemaFactory.byDefault().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    public void loadJsonSchema(JsonNode schemaNode) throws IOException {
        this.schemaNode = schemaNode;
    }

    public ProcessingReport validate(JsonNode dataNode) throws IOException, ProcessingException, InvalidPortfolioMessageException {
        ProcessingReport report = validator.validate(schemaNode, dataNode);
        if (!report.isSuccess()) {
            final JsonNode reportAsJson = ((AsJson) report).asJson();
            throw new InvalidPortfolioMessageException(reportAsJson.toString());
        }
        return report;
    }

    public JsonNode jsonFile2JsonNode(String file) throws IOException {
        URL url = Resources.getResource(file);
        String s = Resources.toString(url, Charsets.UTF_8);
        return mapper.readTree(s);
    }
}
