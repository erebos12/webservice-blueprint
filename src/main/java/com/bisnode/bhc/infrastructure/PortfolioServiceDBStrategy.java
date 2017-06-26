package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;

import java.util.Arrays;
import java.util.List;



public class PortfolioServiceDBStrategy implements PortfolioDataStrategy {

	private TableSelector tableSelector;

	public PortfolioServiceDBStrategy(TableSelector tableSelector) {
		this.tableSelector = tableSelector;
	}

	@Override
	public List<Portfolio> getPortfolioBy(List<Integer> departmentList) {
		String departmentColoumn = "PFL_CDP_ID";
		SelectColumnCriteria critDepartment = new SelectColumnCriteria(departmentColoumn, departmentList);		
		List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
		return portfolioList;	
	}
}
