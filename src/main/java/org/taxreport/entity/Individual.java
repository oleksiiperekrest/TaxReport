package org.taxreport.entity;

public class Individual extends Client {

    private String firstName;
    private String lastName;

    /**Registration number of taxpayer's account*/
    /**10-digit number*/
    private int taxpayerNumber;

    public Individual(Long id, String email, String password, String firstName, String lastName, int taxpayerNumber) {
        super(id, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.taxpayerNumber = taxpayerNumber;
    }
}
