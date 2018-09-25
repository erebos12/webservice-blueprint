package com.alex.ws.infrastructure;

import com.alex.ws.domain.portfolio.Portfolio;
import com.alex.ws.utils.H2DbInitializer;
import com.alex.ws.utils.Sorter;
import com.alex.bhc.domain.portfolio.Portfolio;
import com.alex.bhc.utils.H2DbInitializer;
import com.alex.bhc.utils.Sorter;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PortfolioRepositoryTest {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @BeforeClass
    public static void setup() throws SQLException {
        H2DbInitializer.initializeH2();
    }
    @Before
    public void cleanup(){
        portfolioRepository.deleteAll();
    }

    @Test
    public void whenUpdateCompanyInPortfolio_thenExpect_EndDateForPortfolio_and_NoEndDateFor_UpdatedPortfolio(){
        Portfolio portfolio = new Portfolio();
        portfolio.pfl_cust_identifier = "123";
        portfolio.pfl_country_iso2 = "DE";
        portfolio.pfl_strt_dt = new Date();
        portfolio.pfl_csg_id = 3;
        portfolio.pfl_dtt_id = 1;
        portfolio.pfl_ext_identifier = 444;
        portfolio.pfl_wrk_id = 1;

        Portfolio untouched = new Portfolio();
        untouched.pfl_cust_identifier = "999";
        untouched.pfl_country_iso2 = "SE";
        untouched.pfl_strt_dt = new Date();
        untouched.pfl_csg_id = 2;
        untouched.pfl_dtt_id = 1; //
        untouched.pfl_ext_identifier = 999;
        untouched.pfl_wrk_id = 2;
        portfolioRepository.save(Arrays.asList(portfolio, untouched));
        assertThat(portfolioRepository.findAll().size(), is(2));

        Portfolio portfolio_updated = new Portfolio();
        portfolio_updated.pfl_cust_identifier = "123";
        portfolio_updated.pfl_country_iso2 = "DE";
        portfolio_updated.pfl_strt_dt = new Date();
        portfolio_updated.pfl_csg_id = 3;
        portfolio_updated.pfl_dtt_id = 3; //
        portfolio_updated.pfl_ext_identifier = 444;
        portfolio_updated.pfl_wrk_id = 1;
        portfolioRepository.setEndDateForSpecificId(new Date(), portfolio.pfl_csg_id, portfolio.pfl_cust_identifier);
        portfolioRepository.save(portfolio_updated);
        assertThat(portfolioRepository.findAll().size(), is(3));
        List<Portfolio> resultList = portfolioRepository.findByCustId(portfolio.pfl_cust_identifier);
        assertThat(resultList.size(), is(2));
        Sorter.sortListByPortfolioID(resultList);
        assertThat(resultList.get(0).pfl_end_dt, is(IsNull.notNullValue()));
        assertThat(resultList.get(1).pfl_end_dt, is(IsNull.nullValue()));
        resultList = portfolioRepository.findByCustId(untouched.pfl_cust_identifier);
        assertThat(resultList.size(), is(1));
        assertThat(resultList.get(0).pfl_end_dt, is(IsNull.nullValue()));
    }
}
