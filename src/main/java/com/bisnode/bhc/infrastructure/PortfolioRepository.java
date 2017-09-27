package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    @Query("select p from Portfolio p where p.pfl_csg_id = :pfl_csg_id")
    List<Portfolio> getEntirePortfolioBy(@Param("pfl_csg_id") Integer pfl_csg_id);

    @Transactional
    @Modifying
    @Query("update Portfolio p set p.pfl_end_dt = :now where p.pfl_end_dt = null and p.pfl_csg_id = :pfl_csg_id")
    void setEndDate(@Param("now") Date now, @Param("pfl_csg_id") Integer pfl_csg_id);
}
