package com.bisnode.bhc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by sahm on 28.08.17.
 */

public final class Company {
    public final String id;
    public final String country;
    public final String data_profile;

    @JsonCreator
    public Company(@JsonProperty("id") String id, @JsonProperty("country") String country, @JsonProperty("data_profile") String data_profile) {
        this.id = id;
        this.country = country;
        this.data_profile = data_profile;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}