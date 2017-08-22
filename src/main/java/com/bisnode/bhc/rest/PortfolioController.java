package com.bisnode.bhc.rest;

/**
 * Created by sahm on 21.06.17.
 */

import com.bisnode.bhc.configuration.Config;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequestMapping("/portfolios")
public class PortfolioController implements PortfolioApi {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

    @Autowired
    public PortfolioController(Config config) {
    }

    @Override
    public ResponseEntity getPortfolio(final @PathVariable("id") String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> postPortfolio(@RequestBody String body) {
        logger.info("Receiving POST request: ", body);
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("message", "Received POST request successfully");
        return ResponseEntity.ok(node);
    }
}

