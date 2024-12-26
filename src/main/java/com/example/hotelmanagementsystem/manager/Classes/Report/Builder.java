package com.example.hotelmanagementsystem.manager.Classes.Report;

import java.time.LocalDateTime;

public class Builder {
    private int reportID;
    private String period;
    private double totalIncome;
    private LocalDateTime creationDateTime;

    public Builder setReportID(int reportID) {
        this.reportID = reportID;
        return this;
    }

    public Builder setPeriod(String period) {
        this.period = period;
        return this;
    }

    public Builder setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
        return this;
    }

    public Builder setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
        return this;
    }

    public int getReportID() {
        return reportID;
    }

    public String getPeriod() {
        return period;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public IncomeReport build() {
        return new IncomeReport(this);
    }
}