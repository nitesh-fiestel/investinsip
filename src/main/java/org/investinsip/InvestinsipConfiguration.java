package org.investinsip;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class InvestinsipConfiguration extends Configuration {
    @NotEmpty
    private String appName = "Investinsip";

    @JsonProperty
    public String getAppName() {
        return appName;
    }

    @JsonProperty
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    @NotNull
    private String dataDir = "data";
    
    @JsonProperty
    public String getDataDir() {
        return dataDir;
    }
    
    @JsonProperty
    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }
}
