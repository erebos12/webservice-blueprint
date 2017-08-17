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
public class PortfolioController {

    @Autowired
    public PortfolioController(Config config){
    }

    @ApiOperation(value = "Return portfolio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return portfolio", response = String.class),
            @ApiResponse(code = 404, message = "When no portfolio found", response = String.class)})


    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity getPortfolio(
            @ApiParam(value = "portfolio to be returned") final @PathVariable("id") String id) {
        return null;
    }
}

