package com.bisnode.bhc.rest;

/**
 * Created by sahm on 21.06.17.
 */

import com.bisnode.bhc.configuration.Config;
import io.swagger.annotations.Api;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<JSONObject> postPortfolio(@RequestBody String body) {
        logger.info("Receiving POST request: ", body);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Received POST request successfully");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
    }
}

