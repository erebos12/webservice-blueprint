package com.alex.ws.utils;

import com.alex.bhc.domain.portfolio.Portfolio;

import java.util.List;

/**
 * Created by sahm on 24.08.17.
 */
public class Sorter {

    public static void sortListByPortfolioID(List<Portfolio> portfolioList) {
        portfolioList.sort((Portfolio p1, Portfolio p2) -> {
            if (p1.pfl_id > p2.pfl_id)
                return 1;
            if (p1.pfl_id < p2.pfl_id)
                return -1;
            return 0;
        });
    }
}
