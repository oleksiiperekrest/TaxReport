package org.taxreport.entity;

public final class UserType extends Entity {
    private String userType;

    public UserType(Long id, String userType) {
        super(id);
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }
}
