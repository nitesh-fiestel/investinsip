package org.investinsip;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvestinsipConfiguration extends Configuration {
    
    @JsonProperty("investinsip")
    private AppConfiguration appConfig = new AppConfiguration();
    
    public static class AppConfiguration {
        @NotEmpty
        private String appName = "Investinsip";
        
        @NotNull
        private String dataDir = "data";
        
        @JsonProperty
        public String getAppName() {
            return appName;
        }
        
        @JsonProperty
        public void setAppName(String appName) {
            this.appName = appName;
        }
        
        @JsonProperty
        public String getDataDir() {
            return dataDir;
        }
        
        @JsonProperty
        public void setDataDir(String dataDir) {
            this.dataDir = dataDir;
        }
    }
    
    public AppConfiguration getAppConfig() {
        return appConfig;
    }
    
    public void setAppConfig(AppConfiguration appConfig) {
        this.appConfig = appConfig;
    }
}
