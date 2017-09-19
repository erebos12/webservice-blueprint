package com.bisnode.bhc.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;

import java.io.IOException;

public class JsonSchemaValidator {

    private JsonNode schemaNode;
    private JsonValidator validator = JsonSchemaFactory.byDefault().getValidator();

    public void loadJsonSchema(JsonNode schemaNode) throws IOException {
        this.schemaNode = schemaNode;
    }

    public ProcessingReport validate(JsonNode dataNode) throws IOException, ProcessingException {
        ProcessingReport report = validator.validate(schemaNode, dataNode);
        return report;
    }
}
