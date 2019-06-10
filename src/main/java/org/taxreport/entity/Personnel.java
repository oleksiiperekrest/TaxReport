package org.taxreport.entity;

public abstract class Personnel extends User {

    public static final String ADMIN = "ADMIN";
    public static final String INSPECTOR = "INSPECTOR";
    public static final String DEFAULT = "DEFAULT";

    private String name;
    private String badge;

    public Personnel(Long id, String email, String password, String name, String badge) {
        super(id, email, password);
        this.name = name;
        this.badge = badge;
    }

    public String getName() {
        return name;
    }

    public String getBadge() {
        return badge;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Personnel{");
        sb.append("name='").append(name).append('\'');
        sb.append(", badge='").append(badge).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
