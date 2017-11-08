package com.bisnode.bhc.rest;


import com.bisnode.bhc.infrastructure.PortfolioRepository;
import com.bisnode.bhc.utils.IncomingPortfolioJsonBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsNull;
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
import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sahm on 22.08.17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {PostPortfolioController.class, GetPortfolioController.class}, includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.bisnode.bhc.*")})
@EnableJpaRepositories(basePackages = {"com.bisnode.bhc.infrastructure"})
@AutoConfigureDataJpa
public class PostAndGetPortfolioTest {

    private static final Logger logger = LoggerFactory.getLogger(PostAndGetPortfolioTest.class);

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private IncomingPortfolioJsonBuilder jsonBuilder;

    private final String POST_URL_WITH_DISABLE_ALL = "/portfolios?disable=all";
    private final String POST_URL_WITHOUT_DISABLE = "/portfolios";

    @Before
    public void cleanup() {
        portfolioRepository.deleteAll();
    }

    @Test
    public void when_postPortfolio_thenExpectItInGETPortfolio_and_updateExistingPortfolio() throws Exception {
        String json = jsonBuilder.build()
                .withSystemId("P2R")
                .withCompany("44444", "54435", "DE", "Small")
                .asJson();
        performPostAndCheckResp(POST_URL_WITHOUT_DISABLE, json);
        MvcResult result = performGetAndCheckResp("/portfolios/p2r");
        JsonNode portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray, is(IsNull.notNullValue()));
        assertThat(portfolioArray.size(), is(1));
        assertThat(portfolioArray.get(0).get("pfl_cust_identifier").asInt(), is(44444));
        assertThat(portfolioArray.get(0).get("pfl_ext_identifier").asText(), is("54435"));
        assertThat(portfolioArray.get(0).get("pfl_end_dt").asText(), is("null"));
        assertThat(portfolioArray.get(0).get("pfl_strt_dt").asText(), IsNull.notNullValue());

        json = jsonBuilder.build()
                .withSystemId("P2R")
                .withCompany("44444", "54435", "DE", "medium")
                .asJson();
        performPostAndCheckResp(POST_URL_WITHOUT_DISABLE, json);
        result = performGetAndCheckResp("/portfolios/p2r");

        portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray, is(IsNull.notNullValue()));
        assertThat(portfolioArray.size(), is(2));
        assertThat(portfolioArray.get(0).get("pfl_cust_identifier").asInt(), is(44444));
        assertThat(portfolioArray.get(0).get("pfl_ext_identifier").asText(), is("54435"));
        assertThat(portfolioArray.get(0).get("pfl_strt_dt").asText(), IsNull.notNullValue());
        assertThat(portfolioArray.get(0).get("pfl_end_dt").asText(), IsNull.notNullValue());
        assertThat(portfolioArray.get(1).get("pfl_cust_identifier").asInt(), is(44444));
        assertThat(portfolioArray.get(1).get("pfl_ext_identifier").asText(), is("54435"));
        assertThat(portfolioArray.get(1).get("pfl_strt_dt").asText(), IsNull.notNullValue());
        assertThat(portfolioArray.get(1).get("pfl_end_dt").asText(), is("null"));
    }

    @Test
    public void when_getWithInvalidSystemId_thenExpectEmptyPortfolioList() throws Exception {
        MvcResult result = performGetAndCheckResp("/portfolios/fdjkjhgfdh");
        JsonNode portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray.size(), is(0));
    }

    @Test
    public void getJustActivePortfolios() throws Exception {
        String json = jsonBuilder.build()
                .withSystemId("PBC")
                .withCompany("43257", "54309888", "DE", "Large")
                .withCompany("44561", "54435", "US", "Medium")
                .withCompany("43756823", "12321432", "SE", "Small")
                .asJson();
        performPostAndCheckResp(POST_URL_WITH_DISABLE_ALL, json);
        performPostAndCheckResp(POST_URL_WITH_DISABLE_ALL, json);
        MvcResult result = performGetAndCheckResp("/portfolios/pbc?active=true");
        JsonNode portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray.size(), is(3));
    }

    @Test
    public void DISABLE_OFF_thenExpect_receiveAllOldEntries() throws Exception {
        String json1 = jsonBuilder.build()
                .withSystemId("P2R")
                .withCompany("43257", "54309888", "DE", "Large")
                .withCompany("44561", "54435", "US", "Medium")
                .withCompany("43756823", "12321432", "SE", "Small")
                .asJson();
        performPostAndCheckResp(POST_URL_WITH_DISABLE_ALL, json1);

        String json2 = jsonBuilder.build()
                .withSystemId("P2R")
                .withCompany("43257", "54309888", "DE", "Small")
                .asJson();
        performPostAndCheckResp(POST_URL_WITHOUT_DISABLE, json2);
        MvcResult result = performGetAndCheckResp("/portfolios/p2r?active=true");
        JsonNode portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray.size(), is(3));
    }

    @Test
    public void DISABLE_ALL_thenExpect_notToReceiveOldEntries() throws Exception {
        String json1 = jsonBuilder.build()
                .withSystemId("P2R")
                .withCompany("43257", "54309888", "DE", "Large")
                .withCompany("44561", "54435", "US", "Medium")
                .withCompany("43756823", "12321432", "SE", "Small")
                .asJson();
        performPostAndCheckResp(POST_URL_WITH_DISABLE_ALL, json1);

        String json2 = jsonBuilder.build()
                .withSystemId("P2R")
                .withCompany("43257", "54309888", "DE", "Small")
                .asJson();
        performPostAndCheckResp(POST_URL_WITH_DISABLE_ALL, json2);
        MvcResult result = performGetAndCheckResp("/portfolios/p2r?active=true");
        JsonNode portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray.size(), is(1));
    }

    private MvcResult performPostAndCheckResp(String urlTemplate, String jsonContent) throws Exception {
        MvcResult result = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode jsonNode = resultToJsonNode(result);
        assertThat(jsonNode.get("message").asText(), CoreMatchers.containsString("Portfolio proceeded successfully"));
        return result;
    }

    private MvcResult performGetAndCheckResp(String urlTemplate) throws Exception {
        MvcResult result = mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andReturn();
        return result;
    }

    private JsonNode resultToPortfolioJsonNode(MvcResult result) throws IOException {
        String response = result.getResponse().getContentAsString();
        JsonNode jsonNode = mapper.readTree(response);
        return jsonNode.get("portfolio");
    }

    private JsonNode resultToJsonNode(MvcResult result) throws IOException {
        String response = result.getResponse().getContentAsString();
        return mapper.readTree(response);
    }

}
