package org.taxreport.entity;

import java.util.Objects;

public abstract class User extends Entity {
    private String email;
    private String password;

    public User(Long id, String email, String password) {
        super(id);
        this.email = email;
        this.password = password;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
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
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, password);
    }

}
