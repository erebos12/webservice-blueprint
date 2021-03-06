package com.alex.ws.rest;

/**
 * Created by sahm on 21.06.17.
 */

import com.alex.ws.domain.exception.EmptyPortfolioListException;
import com.alex.ws.domain.portfolio.ConvertPortfolio;
import com.alex.ws.domain.portfolio.IncomingPortfolio;
import com.alex.ws.domain.portfolio.Portfolio;
import com.alex.ws.application.PortfolioManager;
import com.alex.ws.utils.JsonSchemaValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Api
@RequestMapping("/portfolios")
public class PostPortfolioController implements PostPortfolioApi {

    private static final Logger logger = LoggerFactory.getLogger(PostPortfolioController.class);
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ConvertPortfolio converter;
    @Autowired
    private PortfolioManager portfolioManager;
    private JsonSchemaValidator jsonSchemaValidator;
    private final ObjectNode node = JsonNodeFactory.instance.objectNode();
    private final String jsonSchemaFile = "schema.json";

    public PostPortfolioController() throws IOException {
        this.jsonSchemaValidator = new JsonSchemaValidator();
        jsonSchemaValidator.loadJsonSchema(jsonSchemaValidator.jsonFile2JsonNode(jsonSchemaFile));
    }

    @Override
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> postPortfolio(final @RequestBody String body,
                                           final @RequestParam(value = "disable", required = false) String disable) {
        logger.info("Receiving POST request with disable: '{}'", disable);
        try {
            jsonSchemaValidator.validate(mapper.readTree(body));
            List<Portfolio> portfolioList = readFromRequestBody(body);
            handleByDisableParamter(disable, portfolioList);
            String successMsg = String.format("Portfolio proceeded successfully. Uploaded %s records to your portfolio", portfolioList.size());
            node.put("message", successMsg);
            return ResponseEntity.ok(node);
        } catch (Throwable e) {
            logger.error("Exception while proceeding POST request: '{}'", e.toString());
            node.put("message", e.toString());
            return ResponseEntity.status(500).body(node);
        }
    }

    private List<Portfolio> convert2List(IncomingPortfolio incomingPortfolio) throws EmptyPortfolioListException {
        List<Portfolio> portfolioList = converter.apply(incomingPortfolio);
        if (portfolioList.isEmpty()) {
            throw new EmptyPortfolioListException("Empty portfolio list after converting incoming portfolio");
        }
        return portfolioList;
    }

    private void handleByDisableParamter(String disable, List<Portfolio> portfolioList) {
        if ("all".equalsIgnoreCase(disable)) {
            portfolioManager.disableAllAndInsertNewPortfolio(portfolioList);
        } else {
            portfolioManager.disableSpecificAndInsertNewPortfolio(portfolioList);
        }
    }

    private List<Portfolio> readFromRequestBody(String body) throws EmptyPortfolioListException, IOException {
        IncomingPortfolio incomingPortfolio = mapper.readValue(body, IncomingPortfolio.class);
        return convert2List(incomingPortfolio);
    }
}

