package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.response.*;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    Map<String,Object> getHospitalOverview();

    Map<String,Object> getDashboardSummary();
    Map<String,Object> getAdmissionsDischargesActivity();

    Map<String,Object> getSurgeriesEmergenciesActivity();
    Map<String,Object> getSpecializationsDistribution();

    List<Map<String, Object>> getNotifications();
    Map<String, Object> sendNotification(String title, String message);

}
