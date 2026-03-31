package com.revathi.inventory.repositories;

import com.revathi.inventory.model.ReportJobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportJobStatusRepository extends JpaRepository<ReportJobStatus, Long> {
}
