package com.bisnode.bhc.infrastructure;



import com.bisnode.bhc.domain.Portfolio;

import java.util.List;

public class PortfolioStrategyContext {

	private PortfolioDataStrategy portfolioDataStrategy;

	public PortfolioStrategyContext(PortfolioDataStrategy portfolioDataStrategy) {
		this.portfolioDataStrategy = portfolioDataStrategy;
	}

	public List<Portfolio> getPortfolio(List<Integer> departmentList) {
		return portfolioDataStrategy.getPortfolioBy(departmentList);
	}
}
