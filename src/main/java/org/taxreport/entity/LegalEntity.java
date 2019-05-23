package org.taxreport.entity;

public class LegalEntity extends Client {

    private String legalName;

    /**Code of Unified State Register of Enterprises and Organizations of Ukraine*/
    /**8-digit number*/
    private String enterpriseCode;

    public LegalEntity(Long id, String email, String password, String legalName, String enterpriseCode) {
        super(id, email, password);
        this.legalName = legalName;
        this.enterpriseCode = enterpriseCode;
    }
}
