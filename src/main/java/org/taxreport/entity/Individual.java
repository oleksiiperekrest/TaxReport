package org.taxreport.entity;

import java.util.List;
import java.util.Objects;

public class Individual extends Client {

    private String firstName;
    private String lastName;

    /**Registration number of taxpayer's account*/
    /**10-digit number*/
    private String taxpayerNumber;

    public Individual(Long id, String email, String password, List<TaxReport> reports, String firstName, String lastName, String taxpayerNumber) {
        super(id, email, password, reports);
        this.firstName = firstName;
        this.lastName = lastName;
        this.taxpayerNumber = taxpayerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTaxpayerNumber() {
        return taxpayerNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Individual{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", taxpayerNumber='").append(taxpayerNumber).append('\'');
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
        Individual that = (Individual) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(taxpayerNumber, that.taxpayerNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), firstName, lastName, taxpayerNumber);
    }

}
