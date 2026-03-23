package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.request.AdmitPatientRequest;
import nimblix.in.HealthCareHub.response.AdmitPatientResponse;

public interface AdmissionService {

    AdmitPatientResponse admitPatient(AdmitPatientRequest request);
}