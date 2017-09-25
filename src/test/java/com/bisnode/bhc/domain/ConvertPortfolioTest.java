package com.bisnode.bhc.domain;

import com.bisnode.bhc.domain.portfolio.Company;
import com.bisnode.bhc.domain.portfolio.ConvertPortfolio;
import com.bisnode.bhc.domain.portfolio.IncomingPortfolio;
import com.bisnode.bhc.domain.portfolio.Portfolio;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by sahm on 28.08.17.
 */
public class ConvertPortfolioTest {

    private final static ConvertPortfolio converter = new ConvertPortfolio();

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void whenReceiveNormalPortfolio_thenExpectPortfolioList(){
        List<Company> companies = new ArrayList<>();
        Company c1 = new Company("2534", "DE", "Large");
        Company c2 = new Company("4343", "SE", "Medium");
        companies.add(c1);
        companies.add(c2);
        IncomingPortfolio incomingPortfolio = new IncomingPortfolio("2543", "PBC", "1", companies);
        List<Portfolio> portfolios = converter.apply(incomingPortfolio);
        assertThat(portfolios.size(), is(2));
    }
}
