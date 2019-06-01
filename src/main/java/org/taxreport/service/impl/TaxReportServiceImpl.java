package org.taxreport.service.impl;

import org.taxreport.dao.TaxReportDao;
import org.taxreport.entity.TaxReport;
import org.taxreport.service.TaxReportService;

import java.util.List;
import java.util.Optional;

public class TaxReportServiceImpl implements TaxReportService {
    private TaxReportDao taxReportDao;

    public TaxReportServiceImpl(TaxReportDao taxReportDao) {
        this.taxReportDao = taxReportDao;
    }

    @Override
    public void create(TaxReport taxReport) {
        taxReportDao.create(taxReport);
    }

    @Override
    public Optional<TaxReport> getById(Long id) {
        return taxReportDao.getById(id);
    }

    @Override
    public List<TaxReport> getAll() {
        return taxReportDao.getAll();
    }
}
