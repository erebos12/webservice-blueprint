package com.bisnode.bhc.application;

import com.bisnode.bhc.domain.data.ExportData;
import com.bisnode.bhc.domain.portfolio.GlobalMapping;
import com.bisnode.bhc.domain.portfolio.Portfolio;
import com.bisnode.bhc.infrastructure.ExportDataRepository;
import com.bisnode.bhc.infrastructure.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sahm on 23.08.17.
 */
@Component
public class PortfolioManager {

    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private WorkflowDbOperator workflowDbOperator;
    @Autowired
    private ExportDataRepository exportDataRepository;

    public void disableAllAndInsertNewPortfolio(final List<Portfolio> portfolioList) {
        Integer csg_id = portfolioList.get(0).pfl_csg_id;
        portfolioRepository.setEndDateForExistingPortfolio(new Date(), csg_id);
        portfolioList.forEach(portfolio -> portfolioRepository.save(portfolio));
        workflowDbOperator.insertWorkflowFor(csg_id);
    }

    public void disableSpecificAndInsertNewPortfolio(final List<Portfolio> portfolioList) {
        Integer csg_id = portfolioList.get(0).pfl_csg_id;
        portfolioList.forEach(portfolio -> portfolioRepository.setEndDateForSpecificId(new Date(), csg_id, portfolio.pfl_cust_identifier));
        portfolioList.forEach(portfolio -> portfolioRepository.save(portfolio));
        workflowDbOperator.insertWorkflowFor(csg_id);
    }

    public List<Portfolio> getPortfolio(final String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        return portfolioRepository.findByCsgId(mappedSystemId);
    }

    public List<ExportData> getPortfolioData(final String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        return exportDataRepository.findByCsgId(mappedSystemId);
    }

    public List<Portfolio> getActivePortfolio(final String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        List<Portfolio> list = portfolioRepository.findByCsgId(mappedSystemId);
        return list.stream().filter(portItem -> hasNoEndDate(portItem.pfl_end_dt)).collect(Collectors.toList());
    }

    private Integer getSystemIdValue(final String system_id) {
        return GlobalMapping.getSystemIdValue(system_id.toUpperCase());
    }

    private boolean hasNoEndDate(final Date endDate) {
        return endDate == null;
    }
}
