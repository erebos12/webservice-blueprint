package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;

import java.util.List;

public interface PortfolioDataStrategy {

	public List<Portfolio> getPortfolioBy(List<Integer> departmentList);
}
