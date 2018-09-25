package com.alex.ws.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

@Component
public final class IncomingPortfolioJsonBuilder {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private ObjectNode objectNode;
    private ArrayNode arrayNode;


    public IncomingPortfolioJsonBuilder build() {
        objectNode = objectMapper.createObjectNode();
        objectNode.put("company_id_type", "1");
        arrayNode = objectNode.putArray("companies");
        return this;
    }

    public IncomingPortfolioJsonBuilder withSystemId(String system_id) {
        objectNode.put("system_id", system_id);
        return this;
    }

    public IncomingPortfolioJsonBuilder withCompany(String id, String business_partner_id, String country, String data_profile) {
        arrayNode.addObject()
                .put("id", id)
                .put("business_partner_id", business_partner_id)
                .put("country", country)
                .put("data_profile", data_profile);
        return this;
    }

    public String asJson() {
        return objectNode.toString();
    }
}
