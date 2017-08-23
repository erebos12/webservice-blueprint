package com.bisnode.bhc.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sahm on 23.08.17.
 */
public class GetPortfolioController {

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getPortfolio(final @PathVariable("id") String id) {
        return null;
    }
}
