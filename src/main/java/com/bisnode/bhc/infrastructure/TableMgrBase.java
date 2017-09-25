package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.utils.H2DbInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.SQLException;

@Component
public class TableMgrBase {

    protected EntityManagerFactory emf;
    private String persistence_unit;

    @Autowired
    public TableMgrBase(CfgParams cfgParams) throws IOException, SQLException {
        if ("prod".equalsIgnoreCase(cfgParams.mode)) {
            persistence_unit = "portfolio_prod";
        } else {
            persistence_unit = "portfolio_test";
            H2DbInitializer.initializeH2();
        }
        emf = Persistence.createEntityManagerFactory(persistence_unit);
    }
}
