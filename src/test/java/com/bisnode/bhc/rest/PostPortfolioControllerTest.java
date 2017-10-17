package com.bisnode.bhc.rest;


import com.bisnode.bhc.infrastructure.PortfolioRepository;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.net.URL;

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
@EnableJpaRepositories(basePackages = {"com.bisnode.bhc.infrastructure"})
@AutoConfigureDataJpa
public class PostPortfolioControllerTest {

    @Autowired
    private PortfolioRepository portfolioRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostPortfolioControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void cleanup() {
        portfolioRepository.deleteAll();
    }

    @Test
    public void whenSending_portfolio_thenExpect_200OK() throws Exception {
        String json = getFileContent("PBC_Portfolio_With_3_Companies_02.json");
        logger.info("Sending POST with json: '{}'", json);
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();
        String expectedMsg = "Portfolio proceeded successfully";
        assertThat(result.getResponse().getContentAsString(), CoreMatchers.containsString(expectedMsg));
    }

    @Test
    public void sendInvalidDataProfile_thenExpect_500() throws Exception {

        String json = getFileContent("incoming_portfolio_invalid.json");
        logger.info("Sending POST with json: '{}'", json);
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(500))
                .andReturn();
        String expectedString = "/companies/0/data_profile";
        assertThat(result.getResponse().getContentAsString(), CoreMatchers.containsString(expectedString));
    }

    @Test
    public void sendInvalidSystemId_thenExpect_500() throws Exception {
        String json = getFileContent("incoming_portfolio_invalid_systemId.json");
        logger.info("Sending POST with json: '{}'", json);
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(500))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), CoreMatchers.containsString("properties/system_id"));
    }

    @Test
    public void sendEmptyJsonInPost_thenExpect_500() throws Exception {
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().is(500))
                .andReturn();
        String expectedString = "object has missing required properties";
        assertThat(result.getResponse().getContentAsString(), CoreMatchers.containsString(expectedString));
    }

    private String getFileContent(String file) throws IOException {
        URL url = Resources.getResource(file);
        return Resources.toString(url, Charsets.UTF_8);
    }
}
