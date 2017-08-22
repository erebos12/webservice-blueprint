package com.bisnode.bhc.infrastructure;


import com.bisnode.bhc.rest.PortfolioController;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sahm on 22.08.17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PortfolioController.class, includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.bisnode.bhc.*")})
public class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenSending_portfolio_thenExpect_200OK() throws Exception {
        ObjectNode json2send = JsonNodeFactory.instance.objectNode();
        json2send.put("name", "Alex");

        mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(json2send)))
                .andExpect(status().isOk());
    }


}
