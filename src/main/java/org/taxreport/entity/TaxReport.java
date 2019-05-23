package org.taxreport.entity;

import org.taxreport.entity.enums.ReportStatus;

public class TaxReport extends Entity {

    private Client author;
    private String content;
    private ReportStatus reportStatus;
    private User lastUpdate;

    public TaxReport(Long id, Client author, String content, ReportStatus reportStatus, User lastUpdate) {
        super(id);
        this.author = author;
        this.content = content;
        this.reportStatus = reportStatus;
        this.lastUpdate = lastUpdate;
    }
}
