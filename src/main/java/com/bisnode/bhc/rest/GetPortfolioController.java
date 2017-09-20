package com.bisnode.bhc.rest;

import com.bisnode.bhc.application.PortfolioManager;
import com.bisnode.bhc.domain.Portfolio;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by sahm on 23.08.17.
 */

@RestController
@Api
@RequestMapping("/portfolios")
public class GetPortfolioController implements GetPortfolioApi{

    private static final Logger logger = LoggerFactory.getLogger(GetPortfolioController.class);

    @Autowired
    private PortfolioManager portfolioManager;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @RequestMapping(path = "/{system_id}", method = GET, produces = "application/json")
    public ResponseEntity getPortfolio(final @PathVariable("system_id") String system_id) {
        logger.info("Receiving GET request with system_id: '{}'", system_id);
        List<Portfolio> list = portfolioManager.getPortfolio(system_id);
        ArrayNode array = mapper.valueToTree(list);
        JsonNode result = mapper.createObjectNode().set("portfolio", array);
        logger.info("GET response: '{}'", result.toString());
        return ResponseEntity.ok(result);
    }
}
