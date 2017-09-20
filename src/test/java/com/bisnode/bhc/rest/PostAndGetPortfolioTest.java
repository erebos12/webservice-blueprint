package com.bisnode.bhc.rest;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
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

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sahm on 22.08.17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PostPortfolioController.class, includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.bisnode.bhc.*")})
public class PostAndGetPortfolioTest {

    private static final Logger logger = LoggerFactory.getLogger(PostAndGetPortfolioTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void when_postPortfolio_thenExpectItInGETPortfolio_and_updateExistingPortfolio() throws Exception {
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getFileContent("portfolio01.json")))
                .andExpect(status().isOk())
                .andReturn();
        String expectedMsg = "{\"message\":\"Portfolio proceeded successfully\"}";
        assertThat(result.getResponse().getContentAsString(), is(expectedMsg));

        result = mockMvc.perform(get("/portfolios/p2r"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        JsonNode jsonNode = mapper.readTree(response);
        System.out.println(jsonNode.toString());
        JsonNode portfolioArray = jsonNode.get("portfolio");
        assertThat(portfolioArray, is(IsNull.notNullValue()));
        assertThat(portfolioArray.size(), is(1));
        assertThat(portfolioArray.get(0).get("pfl_cust_identifier").asInt(), is(44444));
        assertThat(portfolioArray.get(0).get("pfl_ext_identifier").asInt(), is(1));
        assertThat(portfolioArray.get(0).get("pfl_end_dt").asText(), is("null"));

        result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getFileContent("portfolio02.json")))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), is(expectedMsg));

        result = mockMvc.perform(get("/portfolios/p2r"))
                .andExpect(status().isOk())
                .andReturn();

        response = result.getResponse().getContentAsString();
        jsonNode = mapper.readTree(response);
        System.out.println(jsonNode.toString());
        portfolioArray = jsonNode.get("portfolio");
        assertThat(portfolioArray, is(IsNull.notNullValue()));
        assertThat(portfolioArray.size(), is(2));
        assertThat(portfolioArray.get(0).get("pfl_cust_identifier").asInt(), is(44444));
        assertThat(portfolioArray.get(0).get("pfl_ext_identifier").asInt(), is(1));
        Assert.assertNotEquals(portfolioArray.get(0).get("pfl_end_dt").asText(), "null");
        assertThat(portfolioArray.get(1).get("pfl_cust_identifier").asInt(), is(44444));
        assertThat(portfolioArray.get(1).get("pfl_ext_identifier").asInt(), is(1));
        Assert.assertEquals(portfolioArray.get(1).get("pfl_end_dt").asText(), "null");
    }


    @Test
    public void when_getWithInvalidSystemId_thenExpectEmptyPortfolioList() throws Exception {
        MvcResult result = mockMvc.perform(get("/portfolios/fdjkjhgfdh"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        JsonNode jsonNode = mapper.readTree(response);
        System.out.println(jsonNode.toString());
        JsonNode portfolioArray = jsonNode.get("portfolio");
        assertThat(portfolioArray.size(), is(0));
    }

    private String getFileContent(String file) throws IOException {
        URL url = Resources.getResource(file);
        return Resources.toString(url, Charsets.UTF_8);
    }
}
