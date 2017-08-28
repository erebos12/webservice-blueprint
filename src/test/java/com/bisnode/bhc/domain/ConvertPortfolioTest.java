package com.bisnode.bhc.domain;

import com.bisnode.bhc.domain.exception.InvalidDataProfileException;
import com.bisnode.bhc.domain.exception.InvalidSystemIdException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by sahm on 28.08.17.
 */
public class ConvertPortfolioTest {

    private final static ConvertPortfolio converter = new ConvertPortfolio();

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void whenReceiveNormalPortfolio_thenExpectPortfolioList() throws InvalidDataProfileException, InvalidSystemIdException {
        List<Company> companies = new ArrayList<>();
        Company c1 = new Company("2534", "DE", "Large");
        Company c2 = new Company("4343", "SE", "Medium");
        companies.add(c1);
        companies.add(c2);
        IncomingPortfolio incomingPortfolio = new IncomingPortfolio("2543", "PBC", "1", companies);
        List<Portfolio> portfolios = converter.apply(incomingPortfolio);
        assertThat(portfolios.size(), is(2));
    }

    @Test
    public void whenReceivePortfolioWithInvalidDataProfile_thenExpectException() throws InvalidDataProfileException, InvalidSystemIdException {
        List<Company> companies = new ArrayList<>();
        Company c1 = new Company("2534", "DE", "XXSmall");
        Company c2 = new Company("9999", "SE", "Small");
        companies.add(c1);
        companies.add(c2);
        IncomingPortfolio incomingPortfolio = new IncomingPortfolio("2543", "PBC", "1", companies);
        expectedEx.expect(InvalidDataProfileException.class);
        expectedEx.expectMessage("Invalid data_profile value XXSmall for company-id: 2534");
        converter.apply(incomingPortfolio);
    }

    @Test
    public void whenReceivePortfolioWithInvalidSystemId_thenExpectException() throws InvalidDataProfileException, InvalidSystemIdException {
        String INVALID_SYSTEM_ID = "GGGGGGG";
        List<Company> companies = new ArrayList<>();
        Company c1 = new Company("2534", "DE", "Medium");
        Company c2 = new Company("9999", "SE", "Small");
        companies.add(c1);
        companies.add(c2);
        IncomingPortfolio incomingPortfolio = new IncomingPortfolio("2543", INVALID_SYSTEM_ID, "1", companies);
        expectedEx.expect(InvalidSystemIdException.class);
        String expectedMsg = "Invalid system_id value " + INVALID_SYSTEM_ID + " for company-id: 2534";
        expectedEx.expectMessage(expectedMsg);
        converter.apply(incomingPortfolio);
    }
}
