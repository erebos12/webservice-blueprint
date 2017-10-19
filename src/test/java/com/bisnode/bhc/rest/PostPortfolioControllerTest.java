package com.bisnode.bhc.rest;


import com.bisnode.bhc.infrastructure.PortfolioRepository;
import com.bisnode.bhc.utils.IncomingPortfolioJsonBuilder;
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
    @Autowired
    private IncomingPortfolioJsonBuilder jsonBuilder;

    @Before
    public void cleanup() {
        portfolioRepository.deleteAll();
    }

    @Test
    public void whenSending_portfolio_thenExpect_200OK() throws Exception {
        String json = jsonBuilder.build()
                .withSystemId("PBC")
                .withCompany("43257", "54309888", "DE", "Large")
                .withCompany("44561", "54435", "US", "Medium")
                .withCompany("43756823", "12321432", "SE", "Small")
                .asJson();
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
        String json = jsonBuilder.build()
                .withSystemId("PBC")
                .withCompany("43756823", "12321432", "SE", "InvalidDataProfile")
                .asJson();
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(500))
                .andReturn();
        String expectedString = "instance value (InvalidDataProfile) not found in enum (possible values: [Large,Medium,Small]";
        assertThat(result.getResponse().getContentAsString(), CoreMatchers.containsString(expectedString));
    }

    @Test
    public void sendInvalidSystemId_thenExpect_500() throws Exception {
        String json = jsonBuilder.build()
                .withSystemId("InvalidSystemId")
                .withCompany("43756823", "12321432", "SE", "Small")
                .asJson();
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(500))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), CoreMatchers.containsString("instance value (InvalidSystemId) not found in enum"));
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
}
