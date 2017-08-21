package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.h2.CfgParams;
import com.bisnode.bhc.infrastructure.h2.TestH2Initializer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TableInserterTest {

	private static TableInserter tblInserter = null;
	private static TableSelector tableSelector = null;
	private static final Logger logger = LoggerFactory.getLogger(TableInserterTest.class);

	@BeforeClass
	public static void setup() throws SQLException, RuntimeException, IOException {
		TestH2Initializer.initializeH2(CfgParams.getH2DataFile());
		tblInserter = new TableInserter(CfgParams.getHibernateCfgFile());
		tableSelector = new TableSelector(CfgParams.getHibernateCfgFile());
	}

	@Test
	public void testinsertInto() throws Exception {
		Portfolio p = new Portfolio();
		p.PFL_CLT_ID = 99;
		p.PFL_CDP_ID = 11;
		p.PFL_COUNTRY_ISO3 = "US";
		p.PFL_CUST_ID = "9999";
		p.PFL_CURRENCY_ISO3 = "USD";
		p.PFL_DTT_ID = 8;
		p.PFL_LEDGER_KEY = "SQUID";
		p.PFL_WRK_ID = 1;
		p.PFL_END_DATE = null;
		p.PFL_START_DATE = new Date();
		
		Portfolio p2 = new Portfolio();
		p2.PFL_CLT_ID = 9;
		p2.PFL_CDP_ID = 1;
		p2.PFL_COUNTRY_ISO3 = "RUS";
		p2.PFL_CUST_ID = "555";
		p2.PFL_CURRENCY_ISO3 = "RUS";
		p2.PFL_DTT_ID = 9;
		p2.PFL_LEDGER_KEY = "SQUID";
		p2.PFL_WRK_ID = 1;
		p2.PFL_END_DATE = null;
		p2.PFL_START_DATE = new Date();

		tblInserter.insertInto(Arrays.asList(p, p2));

		SelectColumnProperty critDepartment = new SelectColumnProperty("PFL_CLT_ID", Arrays.asList(9, 99));
		List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
		assertEquals(2, portfolioList.size());
	}

	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void testinsertDuplicates() throws Exception {
		Portfolio originPortfolio = new Portfolio();
		originPortfolio.PFL_CLT_ID = 99;
		originPortfolio.PFL_CDP_ID = 11;
		originPortfolio.PFL_COUNTRY_ISO3 = "US";
		originPortfolio.PFL_CUST_ID = "9999";
		originPortfolio.PFL_CURRENCY_ISO3 = "USD";
		originPortfolio.PFL_DTT_ID = 8;
		originPortfolio.PFL_LEDGER_KEY = "SQUID";
		originPortfolio.PFL_WRK_ID = 1;
		originPortfolio.PFL_END_DATE = null;
		originPortfolio.PFL_START_DATE = new Date();

		Portfolio duplicatePortfolio = new Portfolio();
		originPortfolio.PFL_CLT_ID = 99;
		originPortfolio.PFL_CDP_ID = 11;
		originPortfolio.PFL_COUNTRY_ISO3 = "US";
		originPortfolio.PFL_CUST_ID = "9999";
		originPortfolio.PFL_CURRENCY_ISO3 = "USD";
		originPortfolio.PFL_DTT_ID = 8;
		originPortfolio.PFL_LEDGER_KEY = "SQUID";
		originPortfolio.PFL_WRK_ID = 1;
		originPortfolio.PFL_END_DATE = null;
		originPortfolio.PFL_START_DATE = new Date();

		tblInserter.insertInto(Arrays.asList(originPortfolio, duplicatePortfolio));
	}
}
