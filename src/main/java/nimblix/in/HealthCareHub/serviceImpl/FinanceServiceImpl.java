package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.model.Payment;
import nimblix.in.HealthCareHub.repository.PaymentRepository;
import nimblix.in.HealthCareHub.service.FinanceService;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {

    private final PaymentRepository paymentRepository;

    @Override
    public Double getDailyRevenue() {

        return paymentRepository.fetchTodayRevenue();
    }

    @Override
    public Double getMonthlyRevenue() {

        return paymentRepository.fetchMonthlyRevenue();
    }

    @Override
    public Double getYearlyRevenue() {

        return paymentRepository.fetchYearlyRevenue();
    }

    @Override
    public Map<String, Double> getRevenueSummary() {

        Map<String, Double> revenue = new HashMap<>();

        revenue.put("dailyRevenue", paymentRepository.fetchTodayRevenue());
        revenue.put("monthlyRevenue", paymentRepository.fetchMonthlyRevenue());
        revenue.put("yearlyRevenue", paymentRepository.fetchYearlyRevenue());

        return revenue;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

}