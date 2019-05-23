package org.taxreport.entity;

public class Inspector extends User {

    private String firstName;
    private String lastName;

    public Inspector(Long id, String email, String password, String firstName, String lastName) {
        super(id, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
