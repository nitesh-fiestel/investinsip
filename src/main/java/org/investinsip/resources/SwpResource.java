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
        double yInfl = inflation / 100.0; // annual inflation
        double mInfl = Math.pow(1 + yInfl, 1.0 / 12.0) - 1.0; // monthly inflation rate

        double withdrawnCumulative = 0.0;
        Integer exhaustedYear = null;

        List<String> labels = new ArrayList<>();
        List<Double> corpusSeries = new ArrayList<>();
        List<Double> withdrawnSeries = new ArrayList<>();
        List<Double> realCorpusSeries = new ArrayList<>();

        double finalMonthlyWithdrawal = monthlyWithdrawal;

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

                // Increase withdrawal for next month due to inflation
                monthlyWithdrawal *= (1 + mInfl);

                if (corpus <= 0 && exhaustedYear == null) {
                    exhaustedYear = y;
                    corpus = 0; // clamp
                    // The withdrawal amount just used (before this increment) was the last valid
                    // one.
                    // However, we just incremented it for the "next" month.
                    // To be precise: the user asks for the number at exhaustion.
                    // If we just ran out, the "last monthly withdrawal" is the one we tried to
                    // make.
                    // Since monthlyWithdrawal was incremented *after* the withdrawal logic but
                    // *before* this check,
                    // we should revert the increment or capture the value from arguably the *last
                    // successful* full withdrawal.
                    // But simpler: just capture the current value, as that represents the "cost" at
                    // this time.
                    finalMonthlyWithdrawal = monthlyWithdrawal / (1 + mInfl);
                }
            }
            double realCorpus = corpus / Math.pow(1 + yInfl, y);
            labels.add("Year " + y);
            corpusSeries.add(corpus);
            withdrawnSeries.add(withdrawnCumulative);
            realCorpusSeries.add(realCorpus);
        }

        // If never exhausted, the loop finished, and monthlyWithdrawal is for the
        // (years+1) start.
        // We should just return the final calculated value if not exhausted.
        if (exhaustedYear == null) {
            finalMonthlyWithdrawal = monthlyWithdrawal;
        }

        // Return the last used monthly withdrawal (conceptually the one for the start
        // of the next period)
        return new SwpResponse(labels, corpusSeries, withdrawnSeries, realCorpusSeries,
                withdrawnCumulative, corpus, exhaustedYear, finalMonthlyWithdrawal);
    }
}
