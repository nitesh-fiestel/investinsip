package org.investinsip.resources;

import org.investinsip.api.SwpRequest;
import org.investinsip.api.SwpResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/swp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SwpResource {

    @POST
    public SwpResponse calculate(SwpRequest req) {
        double corpus = Math.max(0, req.getInitial());
        double monthlyWithdrawal = Math.max(0, req.getMonthlyWithdrawal());
        double roi = Math.max(0, req.getRoi());
        double inflation = Math.max(0, req.getInflation());
        int years = Math.max(0, req.getYears());

        double mRate = roi / 100.0 / 12.0; // monthly return
        double yInfl = inflation / 100.0;  // annual inflation

        double withdrawnCumulative = 0.0;
        Integer exhaustedYear = null;

        List<String> labels = new ArrayList<>();
        List<Double> corpusSeries = new ArrayList<>();
        List<Double> withdrawnSeries = new ArrayList<>();
        List<Double> realCorpusSeries = new ArrayList<>();

        for (int y = 1; y <= years; y++) {
            for (int m = 1; m <= 12; m++) {
                // withdraw at start of month
                if (corpus > 0) {
                    double w = Math.min(monthlyWithdrawal, corpus);
                    corpus -= w;
                    withdrawnCumulative += w;
                }
                // monthly compounding on remaining corpus
                corpus *= (1 + mRate);
                if (corpus <= 0 && exhaustedYear == null) {
                    exhaustedYear = y;
                    corpus = 0; // clamp
                }
            }
            double realCorpus = corpus / Math.pow(1 + yInfl, y);
            labels.add("Year " + y);
            corpusSeries.add(corpus);
            withdrawnSeries.add(withdrawnCumulative);
            realCorpusSeries.add(realCorpus);
        }

        return new SwpResponse(labels, corpusSeries, withdrawnSeries, realCorpusSeries,
                withdrawnCumulative, corpus, exhaustedYear);
    }
}
