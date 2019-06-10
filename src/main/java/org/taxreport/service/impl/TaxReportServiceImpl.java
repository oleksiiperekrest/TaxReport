package org.taxreport.service.impl;

import org.taxreport.dao.DaoPool;
import org.taxreport.entity.TaxReport;
import org.taxreport.service.TaxReportService;

import java.util.List;
import java.util.Optional;

public class TaxReportServiceImpl implements TaxReportService {
    private DaoPool daoPool;

    public TaxReportServiceImpl(DaoPool daoPool) {
        this.daoPool = daoPool;
    }

    @Override
    public void create(TaxReport taxReport) {
        daoPool.getTaxReportDao().create(taxReport);
    }

    @Override
    public TaxReport getById(Long id) {
        return  daoPool.getTaxReportDao().getById(id).get();
    }

    @Override
    public List<TaxReport> getAll() {
        return  daoPool.getTaxReportDao().getAll();
    }

    @Override
    public List<TaxReport> getByClientId(Long id) {
        return daoPool.getTaxReportDao().getByClientId(id);
    }


}
