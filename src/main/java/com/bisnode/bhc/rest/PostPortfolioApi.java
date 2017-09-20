package com.bisnode.bhc.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

/**
 * Created by sahm on 17.08.17.
 */
public interface PostPortfolioApi {

    @ApiOperation(value = "Post a portfolio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Portfolio posted successfully", response = String.class),
            @ApiResponse(code = 400, message = "Portfolio message content is invalid", response = String.class)})

    public ResponseEntity<?> postPortfolio(@RequestBody String body) throws IOException;
}


