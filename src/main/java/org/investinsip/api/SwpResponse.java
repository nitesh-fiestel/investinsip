package org.investinsip.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwpResponse {
    private List<String> labels; // Year 1..N
    private List<Double> corpusSeries; // corpus end-of-year (nominal)
    private List<Double> withdrawnSeries; // cumulative withdrawn end-of-year (nominal)
    private List<Double> realCorpusSeries;// corpus deflated by inflation
    private double totalWithdrawn; // final cumulative withdrawn
    private double finalCorpus; // final corpus remaining
    private Integer exhaustedYear; // year when corpus hit <= 0 (if any)
    private double lastMonthlyWithdrawal; // withdrawal amount in the last month/year

    public SwpResponse() {
    }

    public SwpResponse(List<String> labels,
            List<Double> corpusSeries,
            List<Double> withdrawnSeries,
            List<Double> realCorpusSeries,
            double totalWithdrawn,
            double finalCorpus,
            Integer exhaustedYear,
            double lastMonthlyWithdrawal) {
        this.labels = labels;
        this.corpusSeries = corpusSeries;
        this.withdrawnSeries = withdrawnSeries;
        this.realCorpusSeries = realCorpusSeries;
        this.totalWithdrawn = totalWithdrawn;
        this.finalCorpus = finalCorpus;
        this.exhaustedYear = exhaustedYear;
        this.lastMonthlyWithdrawal = lastMonthlyWithdrawal;
    }

    @JsonProperty
    public List<String> getLabels() {
        return labels;
    }

    @JsonProperty
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @JsonProperty
    public List<Double> getCorpusSeries() {
        return corpusSeries;
    }

    @JsonProperty
    public void setCorpusSeries(List<Double> corpusSeries) {
        this.corpusSeries = corpusSeries;
    }

    @JsonProperty
    public List<Double> getWithdrawnSeries() {
        return withdrawnSeries;
    }

    @JsonProperty
    public void setWithdrawnSeries(List<Double> withdrawnSeries) {
        this.withdrawnSeries = withdrawnSeries;
    }

    @JsonProperty
    public List<Double> getRealCorpusSeries() {
        return realCorpusSeries;
    }

    @JsonProperty
    public void setRealCorpusSeries(List<Double> realCorpusSeries) {
        this.realCorpusSeries = realCorpusSeries;
    }

    @JsonProperty
    public double getTotalWithdrawn() {
        return totalWithdrawn;
    }

    @JsonProperty
    public void setTotalWithdrawn(double totalWithdrawn) {
        this.totalWithdrawn = totalWithdrawn;
    }

    @JsonProperty
    public double getFinalCorpus() {
        return finalCorpus;
    }

    @JsonProperty
    public void setFinalCorpus(double finalCorpus) {
        this.finalCorpus = finalCorpus;
    }

    @JsonProperty
    public Integer getExhaustedYear() {
        return exhaustedYear;
    }

    @JsonProperty
    public void setExhaustedYear(Integer exhaustedYear) {
        this.exhaustedYear = exhaustedYear;
    }

    @JsonProperty
    public double getLastMonthlyWithdrawal() {
        return lastMonthlyWithdrawal;
    }

    @JsonProperty
    public void setLastMonthlyWithdrawal(double lastMonthlyWithdrawal) {
        this.lastMonthlyWithdrawal = lastMonthlyWithdrawal;
    }

}
