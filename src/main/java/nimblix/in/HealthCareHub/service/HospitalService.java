package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Review;
import nimblix.in.HealthCareHub.request.HospitalLoginRequest;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;

import nimblix.in.HealthCareHub.request.MedicineAddRequest;
import nimblix.in.HealthCareHub.response.*;

import java.util.List;
import java.util.Map;

public interface HospitalService {


    Long registerHospital(HospitalRegistrationRequest request);
    String addMedicine(MedicineAddRequest request);

    void addRooms(Long hospitalId, List<HospitalRegistrationRequest.Room> rooms);

    Map<String, Object> getHospitalById(Long id);

    List<RoomResponse> getAvailableRooms(Long hospitalId);

    String loginHospital(HospitalLoginRequest request);

    ApiResponse<List<Map<String, Object>>> getAllHospitals();

    ApiResponse<List<Map<String, Object>>> getHospitalReviews(Long hospitalId);


    // Sort hospitals by rating, name, or doctorCount
    List<Hospital> sortHospitals(String sortBy);

    Map<String,Object> getHospitalList();

    ApiResponse<List<Hospital>>searchHospitalsByName(String name);

    Map<String,Object> getWeeklyActivity(Long hospitalId);

    Map<String,Object> getHospitalStats(Long hospitalId);

    Map<String, Object> getHospitalCredentials(Long hospitalId);
    boolean resetHospitalPassword(Long hospitalId, String newPassword);

    Map<String, Object> createRoom(Long hospitalId, String roomNumber, String roomType);
    List<Map<String, Object>> getAllRooms();

    Map<String, Object> getRoomByNumber(String roomNumber);

    Map<String, Object> updateRoom(String roomNumber, String roomType, Boolean available);

    boolean deleteRoom(String roomNumber);

    List<Map<String, Object>> getRoomsByHospital(Long hospitalId);

    Map<String, Object> updateRoomStatus(String roomNumber, Boolean available);

    Map<String,Object> filterHospitals(String specialization);




}
