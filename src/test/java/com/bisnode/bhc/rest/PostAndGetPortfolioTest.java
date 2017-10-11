package com.bisnode.bhc.rest;


import com.bisnode.bhc.infrastructure.PortfolioRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
import java.time.LocalDate;
import java.util.Date;

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

    private final String POST_URL_WITH_DISABLE_ALL = "/portfolios?disable=all";
    private final String POST_URL_WITHOUT_DISABLE = "/portfolios";

    @Before
    public void cleanup() {
        portfolioRepository.deleteAll();
    }

    @Test
    public void when_postPortfolio_thenExpectItInGETPortfolio_and_updateExistingPortfolio() throws Exception {
        performPostAndCheckResp(POST_URL_WITHOUT_DISABLE, "P2R_Portfolio_CompanyId_44444_Profile_Small.json");
        MvcResult result = performGetAndCheckResp("/portfolios/p2r");
        JsonNode portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray, is(IsNull.notNullValue()));
        assertThat(portfolioArray.size(), is(1));
        assertThat(portfolioArray.get(0).get("pfl_cust_identifier").asInt(), is(44444));
        assertThat(portfolioArray.get(0).get("pfl_ext_identifier").asInt(), is(1));
        assertThat(portfolioArray.get(0).get("pfl_end_dt").asText(), is("null"));
        assertThat(portfolioArray.get(0).get("pfl_strt_dt").asText(), is(LocalDate.now().toString()));

        performPostAndCheckResp(POST_URL_WITHOUT_DISABLE, "P2R_Portfolio_CompanyId_44444_Profile_Medium.json");
        result = performGetAndCheckResp("/portfolios/p2r");

        portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray, is(IsNull.notNullValue()));
        assertThat(portfolioArray.size(), is(2));
        assertThat(portfolioArray.get(0).get("pfl_cust_identifier").asInt(), is(44444));
        assertThat(portfolioArray.get(0).get("pfl_ext_identifier").asInt(), is(1));
        Assert.assertNotEquals(portfolioArray.get(0).get("pfl_end_dt").asText(), "null");
        assertThat(portfolioArray.get(1).get("pfl_cust_identifier").asInt(), is(44444));
        assertThat(portfolioArray.get(1).get("pfl_ext_identifier").asInt(), is(1));
        assertThat(portfolioArray.get(0).get("pfl_strt_dt").asText(), is(LocalDate.now().toString()));
        Assert.assertEquals(portfolioArray.get(1).get("pfl_end_dt").asText(), "null");
    }

    @Test
    public void when_getWithInvalidSystemId_thenExpectEmptyPortfolioList() throws Exception {
        MvcResult result = performGetAndCheckResp("/portfolios/fdjkjhgfdh");
        JsonNode portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray.size(), is(0));
    }

    @Test
    public void getJustActivePortfolios() throws Exception {
        performPostAndCheckResp(POST_URL_WITH_DISABLE_ALL, "PBC_Portfolio_With_3_Companies_02.json");
        performPostAndCheckResp(POST_URL_WITH_DISABLE_ALL, "PBC_Portfolio_With_3_Companies_02.json");
        MvcResult result = performGetAndCheckResp("/portfolios/pbc?active=true");
        JsonNode portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray.size(), is(3));
    }

    @Test
    public void DISABLE_OFF_thenExpect_allOldEntries() throws Exception {
        performPostAndCheckResp(POST_URL_WITH_DISABLE_ALL, "P2R_Portfolio_With_3_Companies.json");
        performPostAndCheckResp(POST_URL_WITHOUT_DISABLE, "P2R_Portfolio_CompanyId_44444_Profile_Small.json");
        MvcResult result = performGetAndCheckResp("/portfolios/p2r?active=true");
        JsonNode portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray.size(), is(3));
    }

    @Test
    public void DISABLE_ALL_thenExpect_dontReceiveOldEntries() throws Exception {
        performPostAndCheckResp(POST_URL_WITH_DISABLE_ALL, "P2R_Portfolio_With_3_Companies.json");
        performPostAndCheckResp(POST_URL_WITH_DISABLE_ALL, "P2R_Portfolio_CompanyId_44444_Profile_Small.json");
        MvcResult result = performGetAndCheckResp("/portfolios/p2r?active=true");
        JsonNode portfolioArray = resultToPortfolioJsonNode(result);
        assertThat(portfolioArray.size(), is(1));
    }

    private MvcResult performPostAndCheckResp(String urlTemplate, String fileWithJsonContent) throws Exception {
        MvcResult result = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getFileContent(fileWithJsonContent)))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode jsonNode = resultToJsonNode(result);
        assertThat(jsonNode.get("message").asText(), is("Portfolio proceeded successfully"));
        return result;
    }

    private MvcResult performGetAndCheckResp(String urlTemplate) throws Exception {
        MvcResult result = mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andReturn();
        return result;
    }

    private String getFileContent(String file) throws IOException {
        URL url = Resources.getResource(file);
        return Resources.toString(url, Charsets.UTF_8);
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
