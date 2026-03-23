package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.repository.AdmissionRepository;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.repository.PatientRepository;
import nimblix.in.HealthCareHub.response.*;
import nimblix.in.HealthCareHub.service.DashboardService;
import nimblix.in.HealthCareHub.utility.HealthCareUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AdmissionRepository admissionRepository;

    @Override
    public Map<String,Object> getDashboardSummary() {

        Long totalBeds = hospitalRepository.getTotalBeds();

        Long activeDoctors =
                doctorRepository.countActiveDoctors();

        Long patientsServed =
                patientRepository.count();

        Double averageRating =
                hospitalRepository.getAverageRating();

        // Edge case handling

        if(totalBeds == null)
            totalBeds = 0L;

        if(activeDoctors == null)
            activeDoctors = 0L;

        if(patientsServed == null)
            patientsServed = 0L;

        if(averageRating == null)
            averageRating = 0.0;

        Map<String,Object> response =
                new HashMap<>();

        response.put("totalBeds", totalBeds);
        response.put("activeDoctors", activeDoctors);
        response.put("patientsServed", patientsServed);
        response.put("averageRating", averageRating);

        return response;

    }


    @Override
    public Map<String, Object> getAdmissionsDischargesActivity() {

        LocalDate startDate = LocalDate.now().minusDays(14);

        List<Map<String, Object>> activity =
                admissionRepository.getAdmissionsDischargesLast14Days(startDate);

        if (activity == null || activity.isEmpty()) {
            activity = new ArrayList<>();
        }

        Map<String, Object> response = new HashMap<>();

        response.put(HealthCareConstants.ADMISSION_ACTIVITY, activity);

        return response;
    }

    @Override
    public Map<String,Object>
    getSpecializationsDistribution(){

        List<Map<String,Object>> distribution =
                doctorRepository
                        .getSpecializationsDistribution();

        // Edge case
        if(distribution == null ||
                distribution.isEmpty()){

            throw new RuntimeException(
                    HealthCareConstants
                            .NO_SPECIALIZATION_FOUND);
        }

        Map<String,Object> response =
                new HashMap<>();

        response.put(
                HealthCareConstants
                        .SPECIALIZATION_DISTRIBUTION,
                distribution);

        return response;
    }

    @Override
    public Map<String,Object> getSurgeriesEmergenciesActivity(){

        LocalDate startDate = LocalDate.now().minusDays(14);

        List<Map<String,Object>> activity =
                patientRepository.getSurgeriesEmergenciesLast14Days(startDate);

        if(activity == null || activity.isEmpty()){
            activity = new ArrayList<>();
        }

        Map<String,Object> response = new HashMap<>();

        response.put(HealthCareConstants.SURGERY_ACTIVITY, activity);

        return response;
    }

    @Override
    public Map<String,Object>
    getHospitalOverview(){

        List<Map<String,Object>> hospitals =
                hospitalRepository.getHospitalOverview();

        // Edge case
        if(hospitals == null ||
                hospitals.isEmpty()){

            throw new RuntimeException(
                    HealthCareConstants.NO_HOSPITAL_FOUND);
        }

        Map<String,Object> response =
                new HashMap<>();

        response.put(
                HealthCareConstants.HOSPITAL_OVERVIEW,
                hospitals);

        return response;
    }

    @Override
    public List<Map<String, Object>> getNotifications() {

        List<Map<String, Object>> notifications = new ArrayList<>();

        Map<String, Object> notification1 = new HashMap<>();
        notification1.put("title", "System Maintenance");
        notification1.put("message", "System will be down tonight");
        notification1.put("type", "ADMIN");

        Map<String, Object> notification2 = new HashMap<>();
        notification2.put("title", "New Doctor Registered");
        notification2.put("message", "Dr. John joined the hospital");
        notification2.put("type", "INFO");

        notifications.add(notification1);
        notifications.add(notification2);

        return notifications;
    }

    @Override
    public Map<String, Object> sendNotification(String title, String message) {

        Map<String, Object> notification = new HashMap<>();

        notification.put("title", title);
        notification.put("message", message);
        notification.put("type", "ADMIN");
        notification.put("createdTime",
                HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString());

        return notification;
    }
}
