package org.investinsip.resource;

import com.codahale.metrics.annotation.Timed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Path("/api/investment")
@Produces(MediaType.APPLICATION_JSON)
public class InvestmentResource {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @GET
    @Path("/sip")
    @Timed
    public Map<String, Object> calculateSIP(
            @QueryParam("amount") double monthlyInvestment,
            @QueryParam("rate") double annualRate,
            @QueryParam("years") int years) {
        
        double monthlyRate = (annualRate / 100) / 12;
        int months = years * 12;
        
        // Future Value of SIP formula: FV = P * [((1 + r)^n - 1) / r] * (1 + r)
        double futureValue = monthlyInvestment * 
            ((Math.pow(1 + monthlyRate, months) - 1) / monthlyRate) * 
            (1 + monthlyRate);
            
        double totalInvestment = monthlyInvestment * months;
        double estimatedReturns = futureValue - totalInvestment;
        
        Map<String, Object> result = new HashMap<>();
        result.put("totalInvestment", Math.round(totalInvestment * 100.0) / 100.0);
        result.put("estimatedReturns", Math.round(estimatedReturns * 100.0) / 100.0);
        result.put("totalValue", Math.round(futureValue * 100.0) / 100.0);
        
        return result;
    }

    @GET
    @Path("/swp")
    @Timed
    public Map<String, Object> calculateSWP(
            @QueryParam("amount") double principal,
            @QueryParam("rate") double annualRate,
            @QueryParam("years") int years) {
        
        double monthlyRate = (annualRate / 100) / 12;
        int months = years * 12;
        
        // Calculate monthly withdrawal amount using PMT formula
        // PMT = P * r * (1 + r)^n / ((1 + r)^n - 1)
        double monthlyWithdrawal = principal * monthlyRate * 
            Math.pow(1 + monthlyRate, months) / 
            (Math.pow(1 + monthlyRate, months) - 1);
            
        // Calculate final corpus (should be close to 0, but might have small rounding errors)
        double remainingCorpus = 0;
        double currentPrincipal = principal;
        
        for (int i = 0; i < months; i++) {
            double interest = currentPrincipal * monthlyRate;
            currentPrincipal = currentPrincipal + interest - monthlyWithdrawal;
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("monthlyWithdrawal", Math.round(monthlyWithdrawal * 100.0) / 100.0);
        result.put("totalWithdrawn", Math.round(monthlyWithdrawal * months * 100.0) / 100.0);
        result.put("finalCorpus", Math.round(currentPrincipal * 100.0) / 100.0);
        
        return result;
    }
}
