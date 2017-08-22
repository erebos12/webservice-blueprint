package com.bisnode.bhc.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sahm on 17.08.17.
 */
public interface PortfolioApi {

    @ApiOperation(value = "Return portfolio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return portfolio", response = String.class),
            @ApiResponse(code = 404, message = "When no portfolio found", response = String.class)})

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity getPortfolio(@ApiParam(value = "portfolio to be returned") final @PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> postPortfolio(@RequestBody String body) ;
}


