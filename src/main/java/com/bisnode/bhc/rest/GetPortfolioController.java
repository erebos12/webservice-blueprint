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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sahm on 23.08.17.
 */

@RestController
@Api
@RequestMapping("/portfolios")
public class GetPortfolioController implements GetPortfolioApi {

    private static final Logger logger = LoggerFactory.getLogger(GetPortfolioController.class);

    @Autowired
    private PortfolioManager portfolioManager;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @GetMapping(path = "/{system_id}", produces = "application/json")
    public ResponseEntity getPortfolio(final @PathVariable(value = "system_id") String system_id,
                                       final @RequestParam(value = "active", required = false) String active) {
        logger.info("Receiving GET request with system_id: '{}' and active: '{}'", system_id, active);
        List<Portfolio> list;
        if ("true".equalsIgnoreCase(active)) {
            list = portfolioManager.getActivePortfolio(system_id);
        } else {
            list = portfolioManager.getPortfolio(system_id);
        }
        ArrayNode array = mapper.valueToTree(list);
        JsonNode result = mapper.createObjectNode().set("portfolio", array);
        logger.info("GET response: '{}'", result.toString());
        return ResponseEntity.ok(result);
    }
}
