package org.taxreport.entity;

public abstract class User extends Entity {
    private String email;
    private String password;

    protected User(Long id, String email, String password) {
        super(id);
        this.email = email;
        this.password = password;
    }
}
