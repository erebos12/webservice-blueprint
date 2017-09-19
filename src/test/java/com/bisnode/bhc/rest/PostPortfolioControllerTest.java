package com.bisnode.bhc.rest;


import com.bisnode.bhc.configuration.ResourceHelper;
import org.hamcrest.CoreMatchers;
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
        String expectedString = "/companies/0/data_profile";
        assertThat(result.getResponse().getContentAsString(), CoreMatchers.containsString(expectedString));
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
        assertThat(result.getResponse().getContentAsString(), CoreMatchers.containsString("properties/system_id"));
    }

    @Test
    public void sendEmptyJsonInPost_thenExpect_400() throws Exception {
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String expectedString = "object has missing required properties";
        assertThat(result.getResponse().getContentAsString(), CoreMatchers.containsString(expectedString));
    }
}
