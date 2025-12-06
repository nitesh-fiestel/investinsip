package org.investinsip;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class SimpleConfig extends Configuration {
    @JsonProperty
    private String analyticsFile = "analytics.json";

    public String getAnalyticsFile() {
        return analyticsFile;
    }

    public void setAnalyticsFile(String analyticsFile) {
        this.analyticsFile = analyticsFile;
    }
}
