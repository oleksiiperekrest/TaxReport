package org.taxreport.entity;

import java.util.List;

public class LegalEntity extends Client {

    private String legalName;

    /**Code of Unified State Register of Enterprises and Organizations of Ukraine*/
    /**8-digit number*/
    private String enterpriseCode;

    public LegalEntity(Long id, String email, String password, List<TaxReport> reports, String legalName, String enterpriseCode) {
        super(id, email, password, reports);
        this.legalName = legalName;
        this.enterpriseCode = enterpriseCode;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

}
