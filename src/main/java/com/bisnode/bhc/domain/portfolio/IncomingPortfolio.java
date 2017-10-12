package com.bisnode.bhc.domain.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by sahm on 25.08.17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class IncomingPortfolio {
    public String business_partner_id;
    public String system_id;
    public String company_id_type;
    public List<Company> companies;
}