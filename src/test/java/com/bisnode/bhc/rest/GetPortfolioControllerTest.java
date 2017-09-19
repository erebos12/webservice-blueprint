package com.bisnode.bhc.rest;


import com.google.common.base.Charsets;
import com.google.common.io.Resources;
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

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sahm on 22.08.17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PostPortfolioController.class, includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.bisnode.bhc.*")})
public class GetPortfolioControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(GetPortfolioControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenSending_portfolio_thenExpect_200OK() throws Exception {
        String json = getFileContent("incoming_portfolio01.json");
        MvcResult result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();
        String expectedMsg = "{\"message\":\"Portfolio proceeded successfully\"}";
        assertThat(result.getResponse().getContentAsString(), is(expectedMsg));


        String expectedReponse = "{\"portfolio\":[{\"pfl_id\":1,\"pfl_csg_id\":1,\"pfl_wrk_id\":1,\"pfl_cust_identifier\":\"43257\",\"pfl_ext_identifier\":1,\"pfl_dtt_id\":3,\"pfl_country_iso2\":\"DE\",\"pfl_strt_dt\":1505843655463,\"pfl_end_dt\":null},{\"pfl_id\":2,\"pfl_csg_id\":1,\"pfl_wrk_id\":1,\"pfl_cust_identifier\":\"44561\",\"pfl_ext_identifier\":1,\"pfl_dtt_id\":2,\"pfl_country_iso2\":\"US\",\"pfl_strt_dt\":1505843655463,\"pfl_end_dt\":null},{\"pfl_id\":3,\"pfl_csg_id\":1,\"pfl_wrk_id\":1,\"pfl_cust_identifier\":\"43756823\",\"pfl_ext_identifier\":1,\"pfl_dtt_id\":1,\"pfl_country_iso2\":\"SE\",\"pfl_strt_dt\":1505843655463,\"pfl_end_dt\":null}]}";
        mockMvc.perform(get("/portfolios/PBC")
                .content(expectedReponse))
                .andExpect(status().isOk())
                .andReturn();
    }

    private String getFileContent(String file) throws IOException {
        URL url = Resources.getResource(file);
        return Resources.toString(url, Charsets.UTF_8);
    }
}
