package com.alex.ws.utils;

import com.alex.ws.domain.exception.InvalidPortfolioMessageException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.util.AsJson;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class JsonSchemaValidator {

    private JsonNode schemaNode;
    private final JsonValidator validator = JsonSchemaFactory.byDefault().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    public void loadJsonSchema(JsonNode schemaNode) throws IOException {
        this.schemaNode = schemaNode;
    }

    public ProcessingReport validate(final JsonNode dataNode) throws IOException, ProcessingException, InvalidPortfolioMessageException {
        ProcessingReport report = validator.validateUnchecked(schemaNode, dataNode);
        if (!report.isSuccess()) {
            final JsonNode reportAsJson = ((AsJson) report).asJson();
            String errMsg = reportAsJson.get(0).get("message").asText();
            throw new InvalidPortfolioMessageException(filterMsgString(errMsg));
        }
        return report;
    }

    public JsonNode jsonFile2JsonNode(final String file) throws IOException {
        URL url = Resources.getResource(file);
        String s = Resources.toString(url, Charsets.UTF_8);
        return mapper.readTree(s);
    }

    private String filterMsgString(String inMsg){
        return inMsg.replace("\"", "");
    }
}
