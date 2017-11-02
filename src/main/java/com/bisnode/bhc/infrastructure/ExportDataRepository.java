package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.data.ExportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExportDataRepository extends JpaRepository<ExportData, Integer> {
    @Query("select v from ExportData v where v.pfl_csg_id = :pfl_csg_id")
    List<ExportData> findByCsgId(@Param("pfl_csg_id") Integer pfl_csg_id);
}
