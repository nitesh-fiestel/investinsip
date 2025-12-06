package org.investinsip.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.investinsip.api.AnalyticsData;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class AnalyticsManager {
    private final String filePath;
    private AnalyticsData data;
    private final ObjectMapper mapper;

    public AnalyticsManager(ObjectMapper mapper, String filePath) {
        this.mapper = mapper;
        this.filePath = filePath;
        loadData();
    }

    private void loadData() {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                this.data = mapper.readValue(file, AnalyticsData.class);
            } catch (IOException e) {
                e.printStackTrace();
                this.data = new AnalyticsData();
            }
        } else {
            this.data = new AnalyticsData();
        }
    }

    private void saveData() {
        try {
            mapper.writeValue(new File(filePath), this.data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void recordHit() {
        String today = LocalDate.now().toString();
        this.data.incrementHit(today);
        saveData();
    }

    public synchronized AnalyticsData getData() {
        return this.data;
    }
}
