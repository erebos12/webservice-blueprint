package com.alex.ws.domain.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sahm on 28.08.17.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public final class Company {
    public String id;
    public String business_partner_id;
    public String country;
    public String data_profile;
}