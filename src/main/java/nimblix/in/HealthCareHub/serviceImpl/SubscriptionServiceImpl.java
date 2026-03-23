package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Subscription;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.repository.SubscriptionRepository;
import nimblix.in.HealthCareHub.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final HospitalRepository hospitalRepository;


    @Override
    public List<Map<String, Object>> getHospitalsWithSubscriptions() {

        List<Subscription> subscriptions = subscriptionRepository.findActiveSubscriptions();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Subscription s : subscriptions) {

            Map<String, Object> data = new HashMap<>();

            data.put("subscriptionId", s.getId());
            data.put("hospitalId", s.getHospital().getId());
            data.put("hospitalName", s.getHospital().getName());
            data.put("planType", s.getPlanType());
            data.put("startDate", s.getStartDate());
            data.put("endDate", s.getEndDate());
            data.put("paymentStatus", s.getPaymentStatus());

            result.add(data);
        }

        return result;
    }


    @Override
    public Map<String, Object> addSubscription(Map<String, Object> request) {

        Long hospitalId = Long.valueOf(request.get("hospitalId").toString());
        String planType = request.get("planType").toString();
        Integer durationMonths = Integer.valueOf(request.get("durationMonths").toString());

        Optional<Hospital> hospitalOpt = hospitalRepository.findById(hospitalId);

        if (hospitalOpt.isEmpty()) {
            return null;
        }

        if (subscriptionRepository.existsByHospitalId(hospitalId)) {
            return null;
        }

        Hospital hospital = hospitalOpt.get();

        Subscription subscription = new Subscription();

        subscription.setHospital(hospital);
        subscription.setPlanType(planType);
        subscription.setDurationMonths(durationMonths);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(durationMonths));
        subscription.setPaymentStatus("PENDING");

        Subscription saved = subscriptionRepository.save(subscription);

        Map<String, Object> result = new HashMap<>();

        result.put("subscriptionId", saved.getId());
        result.put("hospitalId", hospital.getId());
        result.put("planType", saved.getPlanType());
        result.put("startDate", saved.getStartDate());
        result.put("endDate", saved.getEndDate());
        result.put("paymentStatus", saved.getPaymentStatus());

        return result;
    }


    @Override
    public Map<String, Object> renewSubscription(Long hospitalId) {

        List<Subscription> subOpt = subscriptionRepository.findByHospital_Id(hospitalId);

        if (subOpt.isEmpty()) {
            return null;
        }

        Subscription subscription = subOpt.get(0);

        LocalDate today = LocalDate.now();


        if (subscription.getEndDate().isBefore(today)) {

            subscription.setStartDate(today);
            subscription.setEndDate(today.plusMonths(subscription.getDurationMonths()));

        } else {

            subscription.setEndDate(subscription.getEndDate()
                    .plusMonths(subscription.getDurationMonths()));
        }

        Subscription updated = subscriptionRepository.save(subscription);

        Map<String, Object> result = new HashMap<>();

        result.put("subscriptionId", updated.getId());
        result.put("hospitalId", hospitalId);
        result.put("newEndDate", updated.getEndDate());
        result.put("paymentStatus", updated.getPaymentStatus());

        return result;
    }

    @Override
    public Map<String, Object> makeSubscriptionPayment(Map<String, Object> request) {

        Long hospitalId = Long.valueOf(request.get("hospitalId").toString());
        Double amount = Double.valueOf(request.get("amount").toString());
        List<Subscription> subOpt = subscriptionRepository.findByHospital_Id(hospitalId);

        if (subOpt.isEmpty()) {
            return null;
        }

        Subscription subscription = subOpt.get(0);

        subscription.setPaymentStatus("PAID");

        Subscription updated = subscriptionRepository.save(subscription);

        Map<String, Object> result = new HashMap<>();

        result.put("subscriptionId", updated.getId());
        result.put("hospitalId", hospitalId);
        result.put("amountPaid", amount);
        result.put("paymentStatus", updated.getPaymentStatus());
        result.put("endDate", updated.getEndDate());

        return result;
    }

    @Override
    public Map<String, Object> updateSubscriptionPlan(Long subscriptionId,
                                                      Map<String, Object> request) {

        Optional<Subscription> optionalSubscription =
                subscriptionRepository.findById(subscriptionId);

        if (optionalSubscription.isEmpty()) {
            return null;
        }

        Subscription subscription = optionalSubscription.get();

        if (request.get("planType") != null) {
            subscription.setPlanType((String) request.get("planType"));
        }

        if (request.get("durationMonths") != null) {
            subscription.setDurationMonths(
                    Integer.parseInt(request.get("durationMonths").toString()));
        }

        if (request.get("paymentStatus") != null) {
            subscription.setPaymentStatus((String) request.get("paymentStatus"));
        }

        subscriptionRepository.save(subscription);

        Map<String, Object> response = new HashMap<>();
        response.put("subscriptionId", subscription.getId());
        response.put("planType", subscription.getPlanType());
        response.put("durationMonths", subscription.getDurationMonths());
        response.put("paymentStatus", subscription.getPaymentStatus());

        return response;
    }

    @Override
    public Map<String, Object> upgradeSubscription(Long subscriptionId,
                                                   Map<String, Object> request) {

        Optional<Subscription> optionalSubscription =
                subscriptionRepository.findById(subscriptionId);

        if (optionalSubscription.isEmpty()) {
            return null;
        }

        Subscription subscription = optionalSubscription.get();

        if (request.get("planType") != null) {
            subscription.setPlanType((String) request.get("planType"));
        }

        if (request.get("durationMonths") != null) {
            subscription.setDurationMonths(
                    Integer.parseInt(request.get("durationMonths").toString()));
        }

        if (request.get("paymentStatus") != null) {
            subscription.setPaymentStatus((String) request.get("paymentStatus"));
        }

        subscriptionRepository.save(subscription);

        Map<String, Object> response = new HashMap<>();
        response.put("subscriptionId", subscription.getId());
        response.put("planType", subscription.getPlanType());
        response.put("durationMonths", subscription.getDurationMonths());
        response.put("paymentStatus", subscription.getPaymentStatus());

        return response;
    }

    @Override
    public Map<String, Object> downgradeSubscription(Long subscriptionId,
                                                     Map<String, Object> request) {

        Optional<Subscription> optionalSubscription =
                subscriptionRepository.findById(subscriptionId);

        if (optionalSubscription.isEmpty()) {
            return null;
        }

        Subscription subscription = optionalSubscription.get();

        if (request.get("planType") != null) {
            subscription.setPlanType((String) request.get("planType"));
        }

        if (request.get("durationMonths") != null) {
            subscription.setDurationMonths(
                    Integer.parseInt(request.get("durationMonths").toString()));
        }

        if (request.get("paymentStatus") != null) {
            subscription.setPaymentStatus((String) request.get("paymentStatus"));
        }

        subscriptionRepository.save(subscription);

        Map<String, Object> response = new HashMap<>();
        response.put("subscriptionId", subscription.getId());
        response.put("planType", subscription.getPlanType());
        response.put("durationMonths", subscription.getDurationMonths());
        response.put("paymentStatus", subscription.getPaymentStatus());

        return response;
    }

    @Override
    public Map<String, Object> extendSubscription(Long subscriptionId,
                                                  Map<String, Object> request) {

        Optional<Subscription> optionalSubscription =
                subscriptionRepository.findById(subscriptionId);

        if (optionalSubscription.isEmpty()) {
            return null;
        }

        Subscription subscription = optionalSubscription.get();

        if (request.get("durationMonths") != null) {

            Integer extraMonths =
                    Integer.parseInt(request.get("durationMonths").toString());

            subscription.setDurationMonths(
                    subscription.getDurationMonths() + extraMonths);

            subscription.setEndDate(
                    subscription.getEndDate().plusMonths(extraMonths));
        }

        subscriptionRepository.save(subscription);

        Map<String, Object> response = new HashMap<>();
        response.put("subscriptionId", subscription.getId());
        response.put("planType", subscription.getPlanType());
        response.put("durationMonths", subscription.getDurationMonths());
        response.put("endDate", subscription.getEndDate());

        return response;
    }

    @Override
    public List<Map<String, Object>> getSubscriptionHistory(Long hospitalId) {

        List<Subscription> subscriptions =
                subscriptionRepository.findByHospital_Id(hospitalId);
        if (subscriptions == null || subscriptions.isEmpty()) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> response = new ArrayList<>();

        for (Subscription subscription : subscriptions) {

            Map<String, Object> map = new HashMap<>();
            map.put("subscriptionId", subscription.getId());
            map.put("planType", subscription.getPlanType());
            map.put("durationMonths", subscription.getDurationMonths());
            map.put("startDate", subscription.getStartDate());
            map.put("endDate", subscription.getEndDate());
            map.put("paymentStatus", subscription.getPaymentStatus());

            response.add(map);
        }

        return response;
    }

    @Override
    public String checkSubscriptionStatus(Long hospitalId) {

        List<Subscription> subscriptions =
                subscriptionRepository.findByHospital_Id(hospitalId);
        if (subscriptions.isEmpty()) {
            return HealthCareConstants.SUBSCRIPTION_NOT_FOUND;
        }

        Subscription subscription = subscriptions.get(0);


        if ("CANCELLED".equalsIgnoreCase(subscription.getPaymentStatus())) {
            return HealthCareConstants.SUBSCRIPTION_CANCELLED;
        }


        if (subscription.getEndDate().isBefore(LocalDate.now())) {
            return HealthCareConstants.SUBSCRIPTION_EXPIRED;
        }


        return HealthCareConstants.SUBSCRIPTION_ACTIVE;
    }

    @Override
    public List<Subscription> getExpiringSubscriptions(int days) {

        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(days);

        return subscriptionRepository.findByEndDateBetween(today, futureDate);
    }

    @Override
    public void sendSubscriptionReminder(Long hospitalId) {

        List<Subscription> subscriptions =
                subscriptionRepository.findByHospital_Id(hospitalId);

        if (subscriptions.isEmpty()) {
            throw new RuntimeException(HealthCareConstants.SUBSCRIPTION_NOT_FOUND);
        }

        Subscription subscription = subscriptions.get(0);

        LocalDate expiryDate = subscription.getEndDate();

        if ("CANCELLED".equalsIgnoreCase(subscription.getPaymentStatus())) {
            throw new RuntimeException(HealthCareConstants.SUBSCRIPTION_CANCELLED);
        }

        if (expiryDate.isBefore(LocalDate.now())) {
            throw new RuntimeException(HealthCareConstants.SUBSCRIPTION_ALREADY_EXPIRED);
        }

        System.out.println("Reminder sent successfully");
    }

    @Override
    public void suspendSubscription(Long hospitalId) {

        List<Subscription> subscriptions =
                subscriptionRepository.findByHospital_Id(hospitalId);

        if (subscriptions.isEmpty()) {
            throw new RuntimeException(HealthCareConstants.SUBSCRIPTION_NOT_FOUND);
        }

        Subscription subscription = subscriptions.get(0);

        if ("SUSPENDED".equalsIgnoreCase(subscription.getPaymentStatus())) {
            throw new RuntimeException(HealthCareConstants.SUBSCRIPTION_ALREADY_SUSPENDED);
        }

        subscription.setPaymentStatus("SUSPENDED");

        subscriptionRepository.save(subscription);
    }

    @Override
    public void reactivateSubscription(Long subscriptionId) {

        Optional<Subscription> subOpt =
                subscriptionRepository.findById(subscriptionId);

        if (subOpt.isEmpty()) {
            throw new RuntimeException(HealthCareConstants.SUBSCRIPTION_NOT_FOUND);
        }

        Subscription subscription = subOpt.get();

        if (!"SUSPENDED".equalsIgnoreCase(subscription.getPaymentStatus())) {
            throw new RuntimeException(HealthCareConstants.SUBSCRIPTION_NOT_SUSPENDED);
        }

        subscription.setPaymentStatus("PAID");

        subscriptionRepository.save(subscription);
    }
}
