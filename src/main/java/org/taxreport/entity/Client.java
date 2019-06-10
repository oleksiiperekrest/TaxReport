package org.taxreport.entity;

import java.util.List;
import java.util.Objects;

public abstract class Client extends User {

    public static final String INDIVIDUAL = "INDIVIDUAL";
    public static final String LEGAL_ENTITY = "LEGAL";

    private List<TaxReport> reports;

    public Client(Long id, String email, String password, List<TaxReport> reports) {
        super(id, email, password);
        this.reports = reports;
    }

    public List<TaxReport> getReports() {
        return reports;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("reports=").append(reports);
        sb.append('}');
        return super.toString() + sb.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(reports, client.reports);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), reports);
    }
}
