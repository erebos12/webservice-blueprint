package com.bisnode.bhc.bdd.stepdefinition;

import com.fasterxml.jackson.databind.JsonNode;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class PostPortfolioTest extends PostPortfolioTestApi {

    private MvcResult result;
    private String json2sent;

    @Given("^the following json:$")
    public void the_following_json(String json2sent) throws Throwable {
        this.json2sent = json2sent;
    }

    @When("^the client sends json by POST to url path /portfolios$")
    public void the_client_sends_json_by_POST_to_portfolios() throws Throwable {
        result = mockMvc.perform(post("/portfolios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2sent))
                .andReturn();
    }

    @Then("^the client receives a response with status code (\\d+)$")
    public void the_client_receives_status_code_of(int expectedCode) throws Throwable {
        assertThat(result.getResponse().getStatus(), is(expectedCode));
    }

    @And("^the response message is \"([^\"]*)\"$")
    public void the_response_message_is(String expectedMsg) throws Throwable {
        JsonNode actualMsg = mapper.readTree(result.getResponse().getContentAsString());
        assertThat(actualMsg.get("message").asText(), is(expectedMsg));
    }
}
