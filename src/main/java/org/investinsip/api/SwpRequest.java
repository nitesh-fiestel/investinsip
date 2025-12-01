package org.investinsip.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SwpRequest {
    private double initial;             // initial corpus
    private double monthlyWithdrawal;   // fixed nominal withdrawal per month
    private double roi;                 // annual return rate %
    private double inflation;           // annual inflation % (for real value calc)
    private int years;                  // number of years

    public SwpRequest() {}

    public SwpRequest(double initial, double monthlyWithdrawal, double roi, double inflation, int years) {
        this.initial = initial;
        this.monthlyWithdrawal = monthlyWithdrawal;
        this.roi = roi;
        this.inflation = inflation;
        this.years = years;
    }

    @JsonProperty public double getInitial() { return initial; }
    @JsonProperty public void setInitial(double initial) { this.initial = initial; }

    @JsonProperty public double getMonthlyWithdrawal() { return monthlyWithdrawal; }
    @JsonProperty public void setMonthlyWithdrawal(double monthlyWithdrawal) { this.monthlyWithdrawal = monthlyWithdrawal; }

    @JsonProperty public double getRoi() { return roi; }
    @JsonProperty public void setRoi(double roi) { this.roi = roi; }

    @JsonProperty public double getInflation() { return inflation; }
    @JsonProperty public void setInflation(double inflation) { this.inflation = inflation; }

    @JsonProperty public int getYears() { return years; }
    @JsonProperty public void setYears(int years) { this.years = years; }
}
