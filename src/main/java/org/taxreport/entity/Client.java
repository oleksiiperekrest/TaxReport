package org.taxreport.entity;

import java.util.List;

public abstract class Client extends User {

    private List<TaxReport> reports;

    public Client(Long id, String email, String password, List<TaxReport> reports) {
        super(id, email, password);
        this.reports = reports;
    }
}
