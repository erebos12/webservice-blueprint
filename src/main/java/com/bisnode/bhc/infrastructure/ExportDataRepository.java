package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.data.ExportData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportDataRepository extends JpaRepository<ExportData, Integer> {
}
