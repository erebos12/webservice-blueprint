package com.alex.ws.bdd.stepdefinition;

import com.alex.ws.infrastructure.PortfolioRepository;
import com.alex.bhc.PortfolioWsMain;
import com.alex.bhc.infrastructure.PortfolioRepository;
import com.alex.bhc.rest.PostPortfolioController;
import com.alex.bhc.utils.IncomingPortfolioJsonBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ContextConfiguration(classes = PortfolioWsMain.class)
@WebMvcTest(controllers = PostPortfolioController.class, includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.alex.bhc.*")})
@EnableJpaRepositories(basePackages = {"com.alex.bhc.infrastructure"})
@AutoConfigureDataJpa
public class PostPortfolioTestApi {
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected IncomingPortfolioJsonBuilder jsonBuilder;
    @Autowired
    protected ObjectMapper mapper;
}
