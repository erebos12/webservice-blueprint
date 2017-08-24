package com.bisnode.bhc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by sahm on 25.08.17.
 */
public final class IncomingPortfolio {
    public final String owner_id;
    public final String system_id;
    public final String business_partner_id;
    public final String company_id_type;
    public final Company companies[];

    @JsonCreator
    public IncomingPortfolio(@JsonProperty("owner_id") String owner_id, @JsonProperty("system_id") String system_id, @JsonProperty("business_partner_id") String business_partner_id, @JsonProperty("company_id_type") String company_id_type, @JsonProperty("companies") Company[] companies){
        this.owner_id = owner_id;
        this.system_id = system_id;
        this.business_partner_id = business_partner_id;
        this.company_id_type = company_id_type;
        this.companies = companies;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static final class Company {
        public final String id;
        public final String country;
        public final String data_profile;

        @JsonCreator
        public Company(@JsonProperty("id") String id, @JsonProperty("country") String country, @JsonProperty("data_profile") String data_profile){
            this.id = id;
            this.country = country;
            this.data_profile = data_profile;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}