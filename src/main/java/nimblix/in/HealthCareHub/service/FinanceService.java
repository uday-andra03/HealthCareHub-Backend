package nimblix.in.HealthCareHub.service;


import nimblix.in.HealthCareHub.model.Payment;

import java.util.List;
import java.util.Map;

public interface FinanceService {

    Double getDailyRevenue();

    Double getMonthlyRevenue();

    Double getYearlyRevenue();

    Map<String, Double> getRevenueSummary();

    List<Payment> getAllPayments();

}
