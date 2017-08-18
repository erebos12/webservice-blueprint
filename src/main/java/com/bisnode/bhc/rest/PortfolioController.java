package com.bisnode.bhc.rest;

/**
 * Created by sahm on 21.06.17.
 */

import com.bisnode.bhc.configuration.Config;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api
@RequestMapping("/portfolios")
public class PortfolioController implements PortfolioApi {

    @Autowired
    public PortfolioController(Config config) {
    }

    public ResponseEntity getPortfolio(final @PathVariable("id") String id) {
        return null;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity postPortfolio() {
        return ResponseEntity.ok(new String("Hello from Portfolio Webservice from Bisnode!"));
    }
}

