package org.investinsip.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SipRequest {
    private double initial;
    private double monthly;
    private double roi; // annual percentage
    private double inflation; // annual percentage
    private int years;

    public SipRequest() {}

    public SipRequest(double initial, double monthly, double roi, double inflation, int years) {
        this.initial = initial;
        this.monthly = monthly;
        this.roi = roi;
        this.inflation = inflation;
        this.years = years;
    }

    @JsonProperty public double getInitial() { return initial; }
    @JsonProperty public void setInitial(double initial) { this.initial = initial; }

    @JsonProperty public double getMonthly() { return monthly; }
    @JsonProperty public void setMonthly(double monthly) { this.monthly = monthly; }

    @JsonProperty public double getRoi() { return roi; }
    @JsonProperty public void setRoi(double roi) { this.roi = roi; }

    @JsonProperty public double getInflation() { return inflation; }
    @JsonProperty public void setInflation(double inflation) { this.inflation = inflation; }

    @JsonProperty public int getYears() { return years; }
    @JsonProperty public void setYears(int years) { this.years = years; }
}
