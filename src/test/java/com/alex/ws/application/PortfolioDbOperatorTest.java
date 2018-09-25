package com.alex.ws.application;

import com.alex.ws.infrastructure.PortfolioRepository;
import com.alex.bhc.domain.portfolio.Portfolio;
import com.alex.bhc.infrastructure.PortfolioRepository;
import com.alex.bhc.utils.H2DbInitializer;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PortfolioDbOperatorTest {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @BeforeClass
    public static void setup() throws SQLException {
        H2DbInitializer.initializeH2();
    }
    @Test
    public void whenInsertToDifferentPortfolios_thenExpect_them_in_DB() throws Exception {
        Portfolio p = new Portfolio();
        p.pfl_country_iso2 = "SE";
        p.pfl_csg_id = 18;
        p.pfl_wrk_id = 77;
        p.pfl_ext_identifier = 777;
        p.pfl_dtt_id = 7777;
        p.pfl_cust_identifier = "77777";
        p.pfl_strt_dt = new Date();
        portfolioRepository.save(p);

        Portfolio p2 = new Portfolio();
        p2.pfl_country_iso2 = "DE";
        p2.pfl_csg_id = 18;
        p2.pfl_wrk_id = 5;
        p2.pfl_ext_identifier = 5;
        p2.pfl_dtt_id = 5;
        p2.pfl_cust_identifier = "5";
        p2.pfl_strt_dt = new Date();
        portfolioRepository.save(p2);

        List<Portfolio> portfolioList = portfolioRepository.findByCsgId(p.pfl_csg_id);
        assertEquals(2, portfolioList.size());
    }

    @Test
    public void whenInsertPortfolio_AndUpdateEnddates_theExpect_NoneNullForEndDates() throws IOException, SQLException {
        Portfolio p = new Portfolio();
        p.pfl_country_iso2 = "SE";
        p.pfl_csg_id = 33;
        p.pfl_wrk_id = 77;
        p.pfl_ext_identifier = 777;
        p.pfl_dtt_id = 7777;
        p.pfl_cust_identifier = "77777";
        p.pfl_strt_dt = new Date();
        portfolioRepository.save(p);

        portfolioRepository.setEndDateForExistingPortfolio(new Date(), p.pfl_csg_id);

        List<Portfolio> l = portfolioRepository.findByCsgId(p.pfl_csg_id);
        assertThat(l.size(), is(1));
        Date endDate = l.get(0).pfl_end_dt;
        assertThat(endDate, is(IsNull.notNullValue()));
    }
}
