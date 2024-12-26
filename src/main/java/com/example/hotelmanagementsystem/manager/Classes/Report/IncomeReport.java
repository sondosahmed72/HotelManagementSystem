package com.example.hotelmanagementsystem.manager.Classes.Report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IncomeReport {
    private int reportID;
    private String period;
    private double totalIncome;
    private LocalDateTime creationDateTime;

    public IncomeReport(Builder builder) {
        this.reportID = builder.getReportID();
        this.period = builder.getPeriod();
        this.totalIncome = builder.getTotalIncome();
        this.creationDateTime = builder.getCreationDateTime();
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getFormattedCreationDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return creationDateTime.format(formatter);
    }
}