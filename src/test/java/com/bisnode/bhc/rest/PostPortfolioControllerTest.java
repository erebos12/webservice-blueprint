package com.bisnode.bhc.rest;


import com.bisnode.bhc.utils.ResourceHelper;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sahm on 22.08.17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PostPortfolioController.class, includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.bisnode.bhc.*")})
public class PostPortfolioControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(PostPortfolioControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenSending_portfolio_thenExpect_200OK() throws Exception {
        String json = ResourceHelper.getResourceAsString("incoming_portfolio01.json");
        logger.info("Sending POST with json: '{}'", json);
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();
        String expectedMsg = "{\"message\":\"Portfolio proceeded successfully\"}";
        assertThat(result.getResponse().getContentAsString(), is(expectedMsg));
    }

    @Test
    public void sendInvalidDataProfile_thenExpect_400() throws Exception {
        String json = ResourceHelper.getResourceAsString("incoming_portfolio_invalid.json");
        logger.info("Sending POST with json: '{}'", json);
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
        String expectedMsg = "{\"message\":\"com.bisnode.bhc.domain.exception.InvalidDataProfileException: Invalid data_profile value jhsghfdgjsd for company-id: 43257\"}";
        assertThat(result.getResponse().getContentAsString(), is(expectedMsg));
    }

    @Test
    public void sendInvalidSystemId_thenExpect_400() throws Exception {
        String json = ResourceHelper.getResourceAsString("incoming_portfolio_invalid_systemId.json");
        logger.info("Sending POST with json: '{}'", json);
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
        String expectedMsg = "{\"message\":\"com.bisnode.bhc.domain.exception.InvalidSystemIdException: Invalid system_id value zzzzzzzzzzzzzz for company-id: 43257\"}";
        assertThat(result.getResponse().getContentAsString(), is(expectedMsg));
    }
}
