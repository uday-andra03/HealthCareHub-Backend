package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.repository.SpecializationRepository;
import nimblix.in.HealthCareHub.response.LabResultResponse;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.LabResult;
import nimblix.in.HealthCareHub.model.Patient;

import nimblix.in.HealthCareHub.repository.LabResultRepository;
import nimblix.in.HealthCareHub.repository.PatientRepository;
import nimblix.in.HealthCareHub.service.LabResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LabResultServiceImpl implements LabResultService {

    @Autowired
    private LabResultRepository labResultRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;


    @Override
    public List<LabResultResponse> getLabResultsByPatient(Long patientId) {

        // Validate patient exists
        Optional<Patient> patient = patientRepository.findById(patientId);

        if (patient.isEmpty()) {
            return null;
        }
        List<LabResult> results = labResultRepository.findByPatientId(patientId);

        return results.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }



    private LabResultResponse mapToResponse(LabResult labResult) {

        LabResultResponse response = new LabResultResponse();

        // Lab result info
        response.setResultId(labResult.getResultId());
        response.setTestName(labResult.getTestName());
        response.setTestCategory(labResult.getTestCategory());
        response.setResult(labResult.getResult());
        response.setReferenceRange(labResult.getReferenceRange());
        response.setUnit(labResult.getUnit());
        response.setStatus(labResult.getStatus());
        response.setRemarks(labResult.getRemarks());
        response.setTestedAt(labResult.getTestedAt());

        // Fetch Patient manually
        Patient patient = patientRepository.findById(labResult.getPatientId())
                .orElse(null);

        if (patient != null) {
            response.setPatientId(patient.getId());
            response.setPatientName(patient.getName());
            response.setPatientPhone(patient.getPhone());
        }

        // Fetch Doctor manually
        Doctor doctor = doctorRepository.findById(labResult.getDoctorId())
                .orElse(null);

        if (doctor != null) {
            response.setDoctorId(doctor.getId());
            response.setDoctorName(doctor.getName());
            response.setDoctorSpecialization(doctor.getSpecialization().getName());


        }

        return response;
    }
}