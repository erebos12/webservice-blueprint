package com.alex.ws.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by sahm on 17.08.17.
 */
public interface GetPortfolioDataApi {

    @ApiOperation(value = "Get portfolio export data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return portfolio company data for a certain system id", response = String.class)})

    public ResponseEntity getPortfolioData(final @PathVariable("system_id") String system_id);
}


