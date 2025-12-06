package org.investinsip.resources;

import org.investinsip.api.AnalyticsData;
import org.investinsip.core.AnalyticsManager;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/analytics")
@Produces(MediaType.APPLICATION_JSON)
public class AnalyticsResource {
    private final AnalyticsManager manager;

    public AnalyticsResource(AnalyticsManager manager) {
        this.manager = manager;
    }

    @POST
    @Path("/hit")
    public void recordHit() {
        manager.recordHit();
    }

    @GET
    @Path("/stats")
    public AnalyticsData getStats() {
        return manager.getData();
    }
}
