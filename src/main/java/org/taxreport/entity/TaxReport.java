package org.taxreport.entity;

import java.time.LocalDateTime;
import java.util.List;

public class TaxReport extends Entity {

    private Client author;
    private String content;
    private LocalDateTime creationTime;
    private ReportStatus reportStatus;
    private Personnel inspector;
    private List<Personnel> rejectedInspectors;
    private LocalDateTime lastUpdatedTime;

    public TaxReport(Long id, Client author, String content, LocalDateTime creationTime, ReportStatus reportStatus,
                     Personnel inspector, List<Personnel> rejectedInspectors, LocalDateTime lastUpdatedTime) {
        super(id);
        this.author = author;
        this.content = content;
        this.creationTime = creationTime;
        this.reportStatus = reportStatus;
        this.inspector = inspector;
        this.rejectedInspectors = rejectedInspectors;
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Client getAuthor() {
        return author;
    }

    public void setAuthor(Client author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public Personnel getInspector() {
        return inspector;
    }

    public List<Personnel> getRejectedInspectors() {
        return rejectedInspectors;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaxReport{");
        sb.append("author=").append(author.getEmail());
        sb.append(", content='").append(content).append('\'');
        sb.append(", creationTime=").append(creationTime);
        sb.append(", reportStatus=").append(reportStatus);
        sb.append(", inspector=").append(inspector);
        sb.append(", rejectedInspectors=").append(rejectedInspectors);
        sb.append(", lastUpdatedTime=").append(lastUpdatedTime);
        sb.append('}');
        return super.toString() + sb.toString();
    }
}
