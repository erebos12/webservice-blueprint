package com.bisnode.bhc.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by sahm on 17.08.17.
 */
public interface GetPortfolioApi {

    @ApiOperation(value = "Get portfolio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a Portfolio for a certain system id", response = String.class)})

    public ResponseEntity getPortfolio(final @PathVariable("system_id") String system_id,
                                       final @RequestParam("active") String active);
}


