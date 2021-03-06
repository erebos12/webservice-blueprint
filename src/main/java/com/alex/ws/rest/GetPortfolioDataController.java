package com.alex.ws.rest;

import com.alex.ws.domain.data.ExportData;
import com.alex.ws.application.PortfolioManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sahm on 23.08.17.
 */

@RestController
@Api
@RequestMapping("/portfolios/data")
public class GetPortfolioDataController implements GetPortfolioDataApi {

    private static final Logger logger = LoggerFactory.getLogger(GetPortfolioDataController.class);

    @Autowired
    private PortfolioManager portfolioManager;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @GetMapping(path = "/{system_id}", produces = "application/json")
    public ResponseEntity getPortfolioData(final @PathVariable(value = "system_id") String system_id) {
        List<ExportData> exportData = portfolioManager.getPortfolioData(system_id);
        ArrayNode array = mapper.valueToTree(exportData);
        JsonNode result = mapper.createObjectNode().set("portfolio", array);
        logger.info("GET response: '{}'", result.toString());
        return ResponseEntity.ok(result);
    }
}
