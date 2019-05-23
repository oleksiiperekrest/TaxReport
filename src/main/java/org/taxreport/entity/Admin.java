package org.taxreport.entity;

import org.taxreport.entity.enums.AdminLevel;

public class Admin extends User {

    private AdminLevel adminLevel;

    public Admin(Long id, String email, String password, AdminLevel adminLevel) {
        super(id, email, password);
        this.adminLevel = adminLevel;
    }
}
