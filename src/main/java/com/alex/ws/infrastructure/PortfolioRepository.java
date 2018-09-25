package com.alex.ws.infrastructure;

import com.alex.ws.domain.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {

    @Query("select p from Portfolio p where p.pfl_csg_id = :pfl_csg_id")
    List<Portfolio> findByCsgId(@Param("pfl_csg_id") Integer pfl_csg_id);

    @Query("select p from Portfolio p where p.pfl_cust_identifier = :pfl_cust_identifier")
    List<Portfolio> findByCustId(@Param("pfl_cust_identifier") String pfl_cust_identifier);

    @Transactional
    @Modifying
    @Query("update Portfolio p set p.pfl_end_dt = :now where p.pfl_end_dt = null and p.pfl_csg_id = :pfl_csg_id")
    void setEndDateForExistingPortfolio(@Param("now") Date now, @Param("pfl_csg_id") Integer pfl_csg_id);

    @Transactional
    @Modifying
    @Query("update Portfolio p set p.pfl_end_dt = :now where p.pfl_end_dt = null and p.pfl_csg_id = :pfl_csg_id and p.pfl_cust_identifier = :pfl_cust_identifier")
    void setEndDateForSpecificId(@Param("now") Date now, @Param("pfl_csg_id") Integer pfl_csg_id, @Param("pfl_cust_identifier") String pfl_cust_identifier);
}
