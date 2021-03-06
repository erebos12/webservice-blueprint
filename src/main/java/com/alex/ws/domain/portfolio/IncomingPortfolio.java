package com.alex.ws.domain.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by sahm on 25.08.17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class IncomingPortfolio {
    public String system_id;
    public String company_id_type;
    public List<Company> companies;
}