package org.investinsip.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

public class AnalyticsData {
    @JsonProperty
    private long totalHits;

    @JsonProperty
    private Map<String, Long> dailyHits = new HashMap<>();

    public AnalyticsData() {
        this.totalHits = 0;
    }

    public long getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(long totalHits) {
        this.totalHits = totalHits;
    }

    public Map<String, Long> getDailyHits() {
        return dailyHits;
    }

    public void setDailyHits(Map<String, Long> dailyHits) {
        this.dailyHits = dailyHits;
    }

    public void incrementHit(String date) {
        this.totalHits++;
        this.dailyHits.merge(date, 1L, Long::sum);
    }
}
