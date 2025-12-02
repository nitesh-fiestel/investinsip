package org.investinsip.resources;

import com.codahale.metrics.annotation.Timed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

@Path("/api/visits")
@Produces(MediaType.APPLICATION_JSON)
public class VisitResource {
    private final String dataFilePath;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public VisitResource(String dataFilePath) {
        this.dataFilePath = dataFilePath;
        // Ensure the data directory exists
        try {
            Files.createDirectories(Paths.get(dataFilePath).getParent());
        } catch (IOException e) {
            System.err.println("Could not create data directory: " + e.getMessage());
        }
    }

    @GET
    @Timed
    public Map<String, Object> trackVisit() {
        try {
            // Read existing data or create new
            Map<String, Object> data = readDataFile();
            
            // Get or initialize counters
            @SuppressWarnings("unchecked")
            Map<String, Object> counters = (Map<String, Object>) data.computeIfAbsent("visits", k -> new HashMap<>());
            
            // Update total visits
            long totalVisits = ((Number) counters.getOrDefault("total", 0L)).longValue() + 1;
            counters.put("total", totalVisits);
            
            // Update daily visits
            String today = dateFormat.format(new Date());
            @SuppressWarnings("unchecked")
            Map<String, Object> dailyVisits = (Map<String, Object>) counters.computeIfAbsent("daily", k -> new HashMap<>());
            long todayVisits = ((Number) dailyVisits.getOrDefault(today, 0L)).longValue() + 1;
            dailyVisits.put(today, todayVisits);
            
            // Save updated data
            writeDataFile(data);
            
            // Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("today", todayVisits);
            response.put("total", totalVisits);
            response.put("date", today);
            
            return response;
            
        } catch (Exception e) {
            System.err.println("Error tracking visit: " + e.getMessage());
            throw new WebApplicationException("Error tracking visit", 500);
        }
    }

    private Map<String, Object> readDataFile() throws IOException {
        java.nio.file.Path path = java.nio.file.Paths.get(dataFilePath);
        if (!Files.exists(path)) {
            return new HashMap<>();
        }
        try (InputStream input = Files.newInputStream(path)) {
            return objectMapper.readValue(input, new TypeReference<Map<String, Object>>() {});
        }
    }

    private void writeDataFile(Map<String, Object> data) throws IOException {
        try (OutputStream output = Files.newOutputStream(
                java.nio.file.Paths.get(dataFilePath),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {
            objectMapper.writeValue(output, data);
        }
    }
}
