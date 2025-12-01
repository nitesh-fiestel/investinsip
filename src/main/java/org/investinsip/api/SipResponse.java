package org.investinsip.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SipResponse {
    private List<String> labels;
    private List<Double> nominalSeries;
    private List<Double> investedSeries;
    private List<Double> realSeries;
    private double investedTotal;
    private double finalNominal;
    private double finalReal;

    public SipResponse() {}

    public SipResponse(List<String> labels,
                       List<Double> nominalSeries,
                       List<Double> investedSeries,
                       List<Double> realSeries,
                       double investedTotal,
                       double finalNominal,
                       double finalReal) {
        this.labels = labels;
        this.nominalSeries = nominalSeries;
        this.investedSeries = investedSeries;
        this.realSeries = realSeries;
        this.investedTotal = investedTotal;
        this.finalNominal = finalNominal;
        this.finalReal = finalReal;
    }

    @JsonProperty public List<String> getLabels() { return labels; }
    @JsonProperty public void setLabels(List<String> labels) { this.labels = labels; }

    @JsonProperty public List<Double> getNominalSeries() { return nominalSeries; }
    @JsonProperty public void setNominalSeries(List<Double> nominalSeries) { this.nominalSeries = nominalSeries; }

    @JsonProperty public List<Double> getInvestedSeries() { return investedSeries; }
    @JsonProperty public void setInvestedSeries(List<Double> investedSeries) { this.investedSeries = investedSeries; }

    @JsonProperty public List<Double> getRealSeries() { return realSeries; }
    @JsonProperty public void setRealSeries(List<Double> realSeries) { this.realSeries = realSeries; }

    @JsonProperty public double getInvestedTotal() { return investedTotal; }
    @JsonProperty public void setInvestedTotal(double investedTotal) { this.investedTotal = investedTotal; }

    @JsonProperty public double getFinalNominal() { return finalNominal; }
    @JsonProperty public void setFinalNominal(double finalNominal) { this.finalNominal = finalNominal; }

    @JsonProperty public double getFinalReal() { return finalReal; }
    @JsonProperty public void setFinalReal(double finalReal) { this.finalReal = finalReal; }
}
