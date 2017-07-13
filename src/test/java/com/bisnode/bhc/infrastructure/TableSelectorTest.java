package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TableSelectorTest {

	private static TableSelector tableSelector = null;

	@BeforeClass
	public static void setup() throws SQLException, RuntimeException, IOException {
		TestH2Initializer.initializeH2(CfgParams.getH2DataFile());
		tableSelector = new TableSelector(CfgParams.getHibernateCfgFile());
	}


	@Test
	public void select_PFL_CDP_ID_with_123() throws IOException {	
		/*
		 * SQL: select * from Portfolio where PFL_CDP_ID in (123)     
		*/
		SelectColumnCriteria critDepartment = new SelectColumnCriteria("PFL_CDP_ID", Arrays.asList(123));								
		List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
		assertEquals(3, portfolioList.size());			
	}
	
	@Test
	public void select_PFL_CDP_ID_with_123_OR_111() throws IOException {	
		/*
		 * SQL: select * from Portfolio where PFL_CDP_ID in (123, 111)     
		*/
		SelectColumnCriteria critDepartment = new SelectColumnCriteria("PFL_CDP_ID", Arrays.asList(123, 111));								
		List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
		assertEquals(4, portfolioList.size());			
	}
	
	@Test
	public void select_PFL_CDP_ID_with_123_AND_PFL_CUST_ID_with_111() throws IOException {	
		/*
		 * SQL: select * from Portfolio where PFL_CDP_ID in (123) and PFL_CUST_ID in ("111")
		*/
		SelectColumnCriteria critDepartment = new SelectColumnCriteria("PFL_CDP_ID", Arrays.asList(123));
		SelectColumnCriteria critDuns = new SelectColumnCriteria("PFL_CUST_ID",  Arrays.asList("111"));	
		List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment, critDuns));
		assertEquals(1, portfolioList.size());			
	}
	
	@Test
	public void select_PFL_CDP_ID_with_123_AND_PFL_CUST_ID_with_444_OR_111() throws IOException {	
		/*
		 * SQL: select * from Portfolio 
		 *   	where PFL_CDP_ID in (123)
		 *      and   PFL_CUST_ID in ("444", "111")         
		*/
		SelectColumnCriteria critDepartment = new SelectColumnCriteria("PFL_CDP_ID", Arrays.asList(123));					
		SelectColumnCriteria critDuns = new SelectColumnCriteria("PFL_CUST_ID",  Arrays.asList("444", "111"));	
		List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment, critDuns));
		assertEquals(2, portfolioList.size());		
		assertEquals(1, portfolioList.get(0).PFL_CLT_ID);
		assertEquals(5, portfolioList.get(1).PFL_CLT_ID);
	}
	
	@Test
	public void select_PFL_CDP_ID_with_123_AND_PFL_CUST_ID_with_444() throws IOException {	
		/*
		 * SQL: select * from Portfolio 
		 *   	where PFL_CDP_ID in (123)
		 *      and   PFL_CUST_ID in ("444")      
		*/
		SelectColumnCriteria critDepartment = new SelectColumnCriteria("PFL_CDP_ID", Arrays.asList(123));	
		SelectColumnCriteria critDuns = new SelectColumnCriteria("PFL_CUST_ID",  Arrays.asList("444"));					
		List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment, critDuns));
		assertEquals(1, portfolioList.size());	
		assertEquals(5, portfolioList.get(0).PFL_CLT_ID);
	}
	
	@Test
	public void select_PFL_CDP_ID_with_123_AND_PFL_CUST_ID_with_222_OR_444_AND_PFL_CURRENCY_ISO3_with_EUR_OR_SWK() throws IOException {
		/*
		 * SQL: select * from Portfolio 
		 *   	where PFL_CDP_ID in (123)
		 *      and   PFL_CUST_ID in ("222", "444")
		 *      and   PFL_CURRENCY_ISO3 in ("EUR", "SWK")
		*/		
		SelectColumnCriteria critDepartment = new SelectColumnCriteria("PFL_CDP_ID", Arrays.asList(123));				
		SelectColumnCriteria critDuns = new SelectColumnCriteria("PFL_CUST_ID",  Arrays.asList("222", "444"));			
		SelectColumnCriteria critCurrency = new SelectColumnCriteria("PFL_CURRENCY_ISO3", Arrays.asList("EUR", "SWK"));						
		List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment, critDuns, critCurrency));
		assertEquals(2, portfolioList.size());
		assertEquals(2, portfolioList.get(0).PFL_CLT_ID);
		assertEquals(5, portfolioList.get(1).PFL_CLT_ID);
	}
}
