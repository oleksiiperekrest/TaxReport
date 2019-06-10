package org.taxreport.service;

import org.taxreport.entity.TaxReport;

import java.util.List;
import java.util.Optional;

public interface TaxReportService {
    void create(TaxReport taxReport);

    TaxReport getById(Long id);

    List<TaxReport> getAll();
    List<TaxReport> getByClientId(Long id);
}
