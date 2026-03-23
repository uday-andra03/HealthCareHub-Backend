package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.response.LabResultResponse;

import java.util.List;

public interface LabResultService {

    // Get all lab results for a patient
    List<LabResultResponse> getLabResultsByPatient(Long patientId);
}