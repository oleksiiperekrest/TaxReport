package org.taxreport.dao;

import org.taxreport.entity.Client;
import org.taxreport.entity.TaxReport;

import java.util.List;

public interface TaxReportDao extends GenericDao<TaxReport> {
    List<TaxReport> getByClientId(Long id);

    List<TaxReport> getByClientIdWithDummyAuthor(Long id, Client author);
}
