package org.taxreport.entity;



public final class ReportStatus extends Entity {
    private String status;

    public ReportStatus(Long id, String status) {
        super(id);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReportStatus{");
        sb.append("status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
