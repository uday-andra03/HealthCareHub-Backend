package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.model.*;
import nimblix.in.HealthCareHub.repository.*;
import nimblix.in.HealthCareHub.request.AdmitPatientRequest;
import nimblix.in.HealthCareHub.response.AdmitPatientResponse;
import nimblix.in.HealthCareHub.exception.DoctorNotFoundException;
import nimblix.in.HealthCareHub.exception.RoomNotFoundException;
import nimblix.in.HealthCareHub.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdmissionServiceImpl implements AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    @Transactional
    public AdmitPatientResponse admitPatient(AdmitPatientRequest request) {

        // Step 1: Validate Patient
        Patient patient = patientRepository.findById(request.getPatientId()).orElse(null);

        if (patient == null) {
            return null;
        }
        // Step 2: Check if Patient already admitted
        boolean isPatientAlreadyAdmitted =
                admissionRepository.existsByPatientIdAndStatus(request.getPatientId(), "ADMITTED");

        
        if (isPatientAlreadyAdmitted) {
            throw new IllegalArgumentException(
                    "Patient is already admitted. Cannot admit the same patient twice.");
        }

        // Step 3: Validate Doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() ->
                        new DoctorNotFoundException("Doctor not found with id: " + request.getDoctorId()));

        // Step 4: Validate Room
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() ->
                        new RoomNotFoundException("Room not found with id: " + request.getRoomId()));

        // Step 5: Check if Room is occupied
        boolean isRoomOccupied =
                admissionRepository.existsByRoomIdAndStatus(request.getRoomId(), "ADMITTED");

        if (isRoomOccupied) {
            throw new IllegalArgumentException(
                    "Room " + room.getRoomNumber() + " is already occupied. Please select another room.");
        }

        // Step 6: Create Admission
        Admission admission = Admission.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .roomId(request.getRoomId())
                .admissionReason(request.getAdmissionReason())
                .symptoms(request.getSymptoms())
                .initialDiagnosis(request.getInitialDiagnosis())
                .status("ADMITTED")
                .build();

        Admission savedAdmission = admissionRepository.save(admission);

        // Step 7: Update Room Status
        room.setStatus(Room.RoomStatus.OCCUPIED);
        roomRepository.save(room);

        // Step 8: Return Response
        return mapToResponse(savedAdmission, patient, doctor, room);
    }

    private AdmitPatientResponse mapToResponse(Admission admission,
                                               Patient patient,
                                               Doctor doctor,
                                               Room room) {

        AdmitPatientResponse response = new AdmitPatientResponse();

        // Admission Info
        response.setAdmissionId(admission.getAdmissionId());
        response.setAdmissionDate(admission.getAdmissionDate());
        response.setAdmissionReason(admission.getAdmissionReason());
        response.setSymptoms(admission.getSymptoms());
        response.setInitialDiagnosis(admission.getInitialDiagnosis());
        response.setStatus(admission.getStatus());

        // Patient Info
        response.setPatientId(patient.getId());
        response.setPatientName(patient.getName());
        response.setPatientPhone(patient.getPhone());

        // Doctor Info
        response.setDoctorId(doctor.getId());
        response.setDoctorName("Dr. " + doctor.getName());

        // Fetch Specialization (Manual - No Mapping)
        String specializationName = "General";

        if (doctor.getSpecialization() != null) {
            Specialization specialization = specializationRepository
                    .findById(doctor.getSpecialization().getId())
                    .orElse(null);

            if (specialization != null) {
                specializationName = specialization.getName();
            }
        }

        response.setDoctorSpecialization(specializationName);

        // Room Info
        response.setRoomId(room.getRoomId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());

        return response;
    }
}
