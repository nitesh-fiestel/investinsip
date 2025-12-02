package org.investinsip.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitResponse {
    private final long today;
    private final long total;
    private final String date;

    public VisitResponse(long today, long total, String date) {
        this.today = today;
        this.total = total;
        this.date = date;
    }

    @JsonProperty
    public long getToday() {
        return today;
    }

    @JsonProperty
    public long getTotal() {
        return total;
    }

    @JsonProperty
    public String getDate() {
        return date;
    }
}
