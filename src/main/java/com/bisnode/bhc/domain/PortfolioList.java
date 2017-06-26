package com.bisnode.bhc.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

public class PortfolioList {

	private List<Portfolio> portfolioList;

	public List<Portfolio> getPortfolioList() {
		return portfolioList;
	}

	public void setPortfolioList(List<Portfolio> portfolioList) {
		this.portfolioList = portfolioList;
	};

}
