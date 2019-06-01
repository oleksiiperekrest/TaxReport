package org.taxreport.entity;

import java.util.List;

public class Inspector extends Personnel {

    private String description;


    public Inspector(Long id, String email, String password, String name, String badge, String description) {
        super(id, email, password, name, badge);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

//    @Override
//    public String getType() {
//
//        return this.getClass().getSimpleName();
//    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Inspector{");
        sb.append("description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
