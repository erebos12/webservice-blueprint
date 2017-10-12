package com.bisnode.bhc.domain.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sahm on 28.08.17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Company {
    public String id;
    public String country;
    public String data_profile;
}