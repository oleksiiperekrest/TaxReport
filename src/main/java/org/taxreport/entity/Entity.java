package org.taxreport.entity;

import java.util.Objects;

public abstract class Entity {

    private Long id;

    public Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    private Entity(Builder builder) {
//        this.id = builder.id;
//    }
//    protected class Builder {
//        private Long id;
//
//        Builder withId(Long id) {
//            this.id = id;
//            return this;
//        }
//        Entity build() {
//            return new Entity(this);
//        }
//    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entity{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
