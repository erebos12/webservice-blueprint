package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.infrastructure.h2.TestH2Initializer;
import com.bisnode.bhc.utils.Sorter;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TableSelectorTest {

    private static TableSelector tableSelector = null;
    private static TableUpserter tableUpserter = null;
    private static Portfolio p1;
    private static Portfolio p2;
    private static Portfolio p3;
    private static Portfolio p4;

    @BeforeClass
    public static void setup() throws SQLException, RuntimeException, IOException {
        TestH2Initializer.initializeH2(CfgParams.getH2DataFile());
        tableSelector = new TableSelector(CfgParams.getHibernateCfgFile(), Arrays.asList(Portfolio.class));
        tableUpserter = new TableUpserter(CfgParams.getHibernateCfgFile(), Arrays.asList(Portfolio.class));
        insertTestData();
    }

    private static void insertTestData() {
        p1 = PortfolioSampleCfg.getPortfolioCompany1();
        p2 = PortfolioSampleCfg.getPortfolioCompany2();
        p3 = PortfolioSampleCfg.getPortfolioCompany3();
        p4 = PortfolioSampleCfg.getPortfolioCompany4();
        tableUpserter.upsert(p1);
        tableUpserter.upsert(p2);
        tableUpserter.upsert(p3);
        tableUpserter.upsert(p4);
    }

    @Test
    public void select_pfl_country_iso2_with_SE() throws IOException {
        /*
		 * SQL: select * from Portfolio where pfl_country_iso2 in ("SE")
		*/
        SelectColumnProperty critDepartment = new SelectColumnProperty("pfl_country_iso2", Arrays.asList("SE"));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        assertEquals(3, portfolioList.size());
    }

    @Test
    public void select_pfl_id_with_1_OR_2() throws IOException {
		/*
		 * SQL: select * from Portfolio where pfl_id in (1, 2)
		*/
        SelectColumnProperty critDepartment = new SelectColumnProperty("pfl_id", Arrays.asList(1, 2));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        assertEquals(2, portfolioList.size());
    }

    @Test
    public void select_PFL_CDP_ID_with_123_AND_PFL_CUST_ID_with_111() throws IOException {
		/*
		 * SQL: select * from Portfolio where pfl_id in (1) and pfl_cust_identifier in ("33333")
		*/
        SelectColumnProperty critDepartment = new SelectColumnProperty("pfl_id", Arrays.asList(1));
        SelectColumnProperty critDuns = new SelectColumnProperty("pfl_cust_identifier", Arrays.asList("33333"));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment, critDuns));
        assertEquals(1, portfolioList.size());
    }

    @Test
    public void select_pfl_country_iso2_with_SE_AND_pfl_cust_identifier_with_33333_OR_77777() throws IOException {
		/*
		 * SQL: select * from Portfolio 
		 *   	where pfl_country_iso2 in ("SE)
		 *      and   pfl_cust_identifier in ("33333", "77777")
		*/
        SelectColumnProperty critDepartment = new SelectColumnProperty("pfl_country_iso2", Arrays.asList(p2.pfl_country_iso2));
        SelectColumnProperty critDuns = new SelectColumnProperty("pfl_cust_identifier", Arrays.asList(p2.pfl_cust_identifier, p3.pfl_cust_identifier));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment, critDuns));
        Sorter.sortListByPortfolioID(portfolioList);
        assertEquals(2, portfolioList.size());
        assertEquals(p2.pfl_cust_identifier, portfolioList.get(0).pfl_cust_identifier);
        assertEquals(p3.pfl_cust_identifier, portfolioList.get(1).pfl_cust_identifier);
    }

    @Test
    public void select_PFL_CDP_ID_with_123_AND_PFL_CUST_ID_with_222_OR_444_AND_PFL_CURRENCY_ISO3_with_EUR_OR_SWK() throws IOException {
		/*
		 * SQL: select * from Portfolio
		 *      where pfl_wrk_id in ("77", "66")
		 *      and   pfl_country_iso2 in ("SE", "DE)
		*/
        SelectColumnProperty critWorkId = new SelectColumnProperty("pfl_wrk_id", Arrays.asList(77, 66));
        SelectColumnProperty critCountry = new SelectColumnProperty("pfl_country_iso2", Arrays.asList("SE", "DE"));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critCountry, critWorkId));
        assertEquals(3, portfolioList.size());
        //assertEquals(2, portfolioList.get(0).PFL_CLT_ID);
        //assertEquals(5, portfolioList.get(1).PFL_CLT_ID);
    }
}
