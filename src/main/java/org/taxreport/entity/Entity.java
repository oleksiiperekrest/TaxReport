package org.taxreport.entity;

public abstract class Entity {

    private Long id;

    protected Entity(Long id) {
        this.id = id;
    }
}
