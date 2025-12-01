package org.investinsip.resources;

import org.investinsip.api.SipRequest;
import org.investinsip.api.SipResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/sip")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SipResource {

    @POST
    public SipResponse calculate(SipRequest req) {
        double initial = Math.max(0, req.getInitial());
        double monthly = Math.max(0, req.getMonthly());
        double roi = Math.max(0, req.getRoi());
        double inflation = Math.max(0, req.getInflation());
        int years = Math.max(0, req.getYears());

        double mRate = roi / 100.0 / 12.0; // monthly return rate
        double yInfl = inflation / 100.0;  // annual inflation rate

        double value = initial; // nominal value at start
        double invested = initial;
        List<String> labels = new ArrayList<>();
        List<Double> nominalSeries = new ArrayList<>();
        List<Double> investedSeries = new ArrayList<>();
        List<Double> realSeries = new ArrayList<>();

        for (int y = 1; y <= years; y++) {
            for (int m = 1; m <= 12; m++) {
                value += monthly;       // contribution at start of month
                invested += monthly;
                value *= (1 + mRate);   // monthly compounding
            }
            double real = value / Math.pow(1 + yInfl, y);
            labels.add("Year " + y);
            nominalSeries.add(value);
            investedSeries.add(invested);
            realSeries.add(real);
        }

        double finalReal = realSeries.isEmpty() ? 0 : realSeries.get(realSeries.size() - 1);
        return new SipResponse(labels, nominalSeries, investedSeries, realSeries, invested, value, finalReal);
    }
}
