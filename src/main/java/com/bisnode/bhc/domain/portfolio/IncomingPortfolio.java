package com.bisnode.bhc.domain.portfolio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by sahm on 25.08.17.
 */
public final class IncomingPortfolio {
    public final String business_partner_id;
    public final String system_id;
    public final String company_id_type;
    public final List<Company> companies;

    @JsonCreator
    public IncomingPortfolio(@JsonProperty("business_partner_id") String business_partner_id, @JsonProperty("system_id") String system_id, @JsonProperty("company_id_type") String company_id_type, @JsonProperty("companies") List<Company> companies) {
        this.business_partner_id = business_partner_id;
        this.system_id = system_id;
        this.company_id_type = company_id_type;
        this.companies = companies;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}