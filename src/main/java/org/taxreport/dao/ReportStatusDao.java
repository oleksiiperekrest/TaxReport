package org.taxreport.dao;

import org.taxreport.entity.ReportStatus;

import java.util.Optional;

public interface ReportStatusDao extends GenericDao<ReportStatus> {
    Optional<Long> getIdByStatus(String status);
}
