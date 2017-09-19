package com.bisnode.bhc.rest;

/**
 * Created by sahm on 21.06.17.
 */

import com.bisnode.bhc.application.PortfolioManager;
import com.bisnode.bhc.domain.ConvertPortfolio;
import com.bisnode.bhc.domain.IncomingPortfolio;
import com.bisnode.bhc.utils.JsonSchemaValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Api
@RequestMapping("/portfolios")
public class PostPortfolioController implements PostPortfolioApi {

    private static final Logger logger = LoggerFactory.getLogger(PostPortfolioController.class);
    private ObjectMapper mapper;
    private ConvertPortfolio converter;
    private PortfolioManager portfolioManager;
    private JsonSchemaValidator jsonSchemaValidator;
    private final ObjectNode node = JsonNodeFactory.instance.objectNode();
    private final String jsonSchemaFile = "schema.json";

    @Autowired
    public PostPortfolioController(ConvertPortfolio converter, PortfolioManager portfolioManager) throws IOException {
        this.converter = converter;
        this.portfolioManager = portfolioManager;
        this.mapper = new ObjectMapper();
        this.jsonSchemaValidator = new JsonSchemaValidator();
        jsonSchemaValidator.loadJsonSchema(jsonSchemaValidator.jsonFile2JsonNode(jsonSchemaFile));
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> postPortfolio(@RequestBody String body) throws IOException {
        try {
            jsonSchemaValidator.validate(mapper.readTree(body));
            IncomingPortfolio incomingPortfolio = mapper.readValue(body, IncomingPortfolio.class);
            logger.info("Receiving POST request with body: '{}'", incomingPortfolio.toString());
            portfolioManager.update(converter.apply(incomingPortfolio));
            node.put("message", "Portfolio proceeded successfully");
            return ResponseEntity.ok(node);
        } catch (Throwable e) {
            logger.error("Exception while proceeding POST request: '{}'", e.toString());
            node.put("message", e.toString());
            return ResponseEntity.badRequest().body(node);
        }
    }
}

