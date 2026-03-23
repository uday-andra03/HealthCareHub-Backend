package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.model.Subscription;

import java.util.List;
import java.util.Map;

public interface SubscriptionService {

    List<Map<String, Object>> getHospitalsWithSubscriptions();

    Map<String, Object> addSubscription(Map<String, Object> request);

    Map<String, Object> renewSubscription(Long hospitalId);

    Map<String, Object> makeSubscriptionPayment(Map<String, Object> request);

    Map<String, Object> updateSubscriptionPlan(Long subscriptionId,
                                               Map<String, Object> request);

    Map<String, Object> upgradeSubscription(Long subscriptionId,
                                            Map<String, Object> request);

    Map<String, Object> downgradeSubscription(Long subscriptionId,
                                              Map<String, Object> request);

    Map<String, Object> extendSubscription(Long subscriptionId,
                                           Map<String, Object> request);

    List<Map<String, Object>> getSubscriptionHistory(Long hospitalId);

    String checkSubscriptionStatus(Long hospitalId);

    List<Subscription> getExpiringSubscriptions(int days);

    void sendSubscriptionReminder(Long hospitalId);

    void suspendSubscription(Long hospitalId);

    void reactivateSubscription(Long subscriptionId);
}
