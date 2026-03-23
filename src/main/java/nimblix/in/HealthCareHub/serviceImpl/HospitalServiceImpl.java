package nimblix.in.HealthCareHub.serviceImpl;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.exception.DuplicateHospitalException;
import nimblix.in.HealthCareHub.exception.HospitalNotFoundException;
import nimblix.in.HealthCareHub.exception.InvalidCredentialsException;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Medicine;
import nimblix.in.HealthCareHub.model.Review;
import nimblix.in.HealthCareHub.repository.*;
import nimblix.in.HealthCareHub.request.HospitalLoginRequest;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.request.MedicineAddRequest;
import nimblix.in.HealthCareHub.response.*;
import nimblix.in.HealthCareHub.security.JwtUtil;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final MedicineRepository medicineRepository;
    private final AdmissionRepository admissionRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppointmentRepository appointmentRepository;

    private final JwtUtil jwtUtil;


    @Override
    public Long registerHospital(HospitalRegistrationRequest request) {
        if (request == null) {
            throw new ValidationException(HealthCareConstants.REQUEST_BODY_NULL);
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ValidationException(HealthCareConstants.HOSPITAL_NAME_REQUIRED);
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new ValidationException(HealthCareConstants.EMAIL_REQUIRED);
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new ValidationException(HealthCareConstants.PASSWORD_REQUIRED);
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ValidationException(HealthCareConstants.PASSWORD_MISMATCH);
        }
        if (request.getTotalBeds() <= 0) {
            throw new ValidationException(HealthCareConstants.INVALID_TOTAL_BEDS);
        }

        if (hospitalRepository.findByNameAndCityAndState(
                request.getName(), request.getCity(), request.getState()).isPresent()) {
            throw new DuplicateHospitalException(HealthCareConstants.HOSPITAL_ALREADY_EXISTS);
        }

        Hospital hospital = Hospital.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .establishedYear(request.getEstablishedYear())
                .totalBeds(request.getTotalBeds())
                .specializations(request.getSpecializations())
                .doctors(request.getDoctors())
                .aboutHospital(request.getAboutHospital())
                .build();

        Hospital savedHospital = hospitalRepository.save(hospital);
        return savedHospital.getId();
    }

    @Override
    public String loginHospital(HospitalLoginRequest request) {
        if (request == null) {
            throw new ValidationException(HealthCareConstants.REQUEST_BODY_NULL);
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new ValidationException(HealthCareConstants.EMAIL_REQUIRED);
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new ValidationException(HealthCareConstants.PASSWORD_REQUIRED);
        }

        Hospital hospital = hospitalRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new HospitalNotFoundException(HealthCareConstants.HOSPITAL_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), hospital.getPassword())) {
            throw new InvalidCredentialsException(HealthCareConstants.INVALID_CREDENTIALS);
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                hospital.getEmail(),
                hospital.getPassword(),
                new ArrayList<>()
        );

        return jwtUtil.generateToken(userDetails);
    }



    @Override
    public String addMedicine(MedicineAddRequest request){

        //-edge cases---
        //--Check Hospital Exists--
        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new IllegalArgumentException("Hospital Not Found"));

        if (request.getPrice()==null || request.getPrice()<=0){
            throw new IllegalArgumentException("price must be greater than 0");
        }

        if (request.getStockQuantity()==null || request.getStockQuantity()<0){
            throw new IllegalArgumentException("StockQuantity cannot be negative ");
        }

        Optional<Medicine> existing = medicineRepository.findByMedicineNameAndHospital(
                request.getMedicineName(), hospital);
        if (existing.isPresent()){
            throw new IllegalArgumentException("Medicine already exists in this hospital");
        }

        //--Create Medicine--
        Medicine medicine = Medicine.builder()
                .medicineName(request.getMedicineName())
                .manufacturer(request.getManufacturer())
                .description(request.getDescription())
                .dosage(request.getDosage())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .isActive("ACTIVE")
                .hospital(hospital)
                .build();

        //--Save medicine
        medicineRepository.save(medicine);
        return "Medicine Added Successfully";
    }

    @Override
    public void addRooms(Long hospitalId,
                         List<HospitalRegistrationRequest.Room> rooms) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("Hospital not found"));

        if (hospital.getRooms().size() + rooms.size() > hospital.getTotalBeds()) {
            throw new IllegalArgumentException("Exceeds total bed capacity");
        }

        for (HospitalRegistrationRequest.Room roomReq : rooms) {
            Hospital.Room room = new Hospital.Room();
            room.setRoomNumber(roomReq.getRoomNumber());
            room.setRoomType(roomReq.getRoomType());
            room.setAvailable(roomReq.isAvailable());

            hospital.getRooms().add(room);
        }

        hospitalRepository.save(hospital);
    }

    @Override
    public List<RoomResponse> getAvailableRooms(Long hospitalId) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("Hospital not found"));

        List<RoomResponse> response = new ArrayList<>();

        for (Hospital.Room room : hospital.getRooms()) {
            if (room.isAvailable()) {
                RoomResponse roomResponse = RoomResponse.builder()
                        .roomNumber(room.getRoomNumber())
                        .roomType(room.getRoomType())
                        .available(room.isAvailable())
                        .build();

                response.add(roomResponse);
            }
        }

        return response;
    }

    @Override
    public ApiResponse<List<Map<String, Object>>> getAllHospitals(){

        List<Map<String, Object>> hospitals = hospitalRepository.findAllHospitals();

        if (hospitals == null || hospitals.isEmpty()){
            throw new IllegalArgumentException(HealthCareConstants.NO_HOSPITALS_FOUND);
        }

        ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>();
        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.HOSPITAL_FETCHED_SUCCESS);
        response.setData(hospitals);

        return response;
    }

    @Override
    public Map<String, Object> getHospitalById(Long id) {
        log.info(HealthCareConstants.HOSPITAL_FETCHED_SUCCESSFULLY);

        if (id <= 0) {
            throw new IllegalArgumentException(HealthCareConstants.INVALID_HOSPITAL_ID); // ✅ changed
        }

        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new HospitalNotFoundException(  // ✅ changed
                        HealthCareConstants.HOSPITAL_NOT_FOUND));

        Map<String, Object> hospitalData = new LinkedHashMap<>();
        hospitalData.put("id", hospital.getId());
        hospitalData.put("name", hospital.getName());
        hospitalData.put("address", hospital.getAddress());
        hospitalData.put("city", hospital.getCity());
        hospitalData.put("state", hospital.getState());
        hospitalData.put("phone", hospital.getPhone());
        hospitalData.put("email", hospital.getEmail());
        hospitalData.put("totalBeds", hospital.getTotalBeds());
        hospitalData.put("specializations", hospital.getSpecializations());
        hospitalData.put("aboutHospital", hospital.getAboutHospital());
        hospitalData.put("doctorCount", hospital.getDoctorCount());
        hospitalData.put("rating", hospital.getRating());
        hospitalData.put("createdTime", hospital.getCreatedTime());
        hospitalData.put("updatedTime", hospital.getUpdatedTime());
        return hospitalData;
    }

    @Override
    public ApiResponse<List<Map<String, Object>>> getHospitalReviews(Long hospitalId) {

        if (hospitalId == null || hospitalId <= 0){
            throw new IllegalArgumentException(HealthCareConstants.INVALID_HOSPITAL_ID);
        }

        if (!hospitalRepository.existsById(hospitalId)){
            throw new HospitalNotFoundException(
                    HealthCareConstants.HOSPITAL_NOT_FOUND + hospitalId);
        }

        List<Map<String, Object>> reviews = reviewRepository.findHospitalReviews(hospitalId);

        ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>();
        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.REVIEWS_FETCHED_SUCCESS);
        response.setData(reviews);

        return response;
    }



    // ⭐ Sorting Hospitals
    @Override

    public List<Hospital> sortHospitals(String sortBy) {

        if (sortBy == null || sortBy.trim().isEmpty()) {

            throw new IllegalArgumentException("SortBy parameter cannot be empty");

        }

        String sortValue = sortBy.trim().toLowerCase();

        switch (sortValue) {

            case HealthCareConstants.SORT_RATING:

                return hospitalRepository.findAll(Sort.by(Sort.Direction.DESC, "rating"));

            case HealthCareConstants.SORT_NAME:

                return hospitalRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

            case HealthCareConstants.SORT_DOCTOR_COUNT:

                return hospitalRepository.findAll(Sort.by(Sort.Direction.DESC, "doctorCount"));

            default:

                throw new IllegalArgumentException("Invalid sortBy value: " + sortBy);

        }

    }


    @Override
    public Map<String,Object> getHospitalList() {

        List<Map<String,Object>> hospitals =
                hospitalRepository.getHospitalDropdownList();

        if(hospitals == null || hospitals.isEmpty()){

            throw new RuntimeException(
                    HealthCareConstants.NO_HOSPITAL_FOUND);
        }

        Map<String,Object> response =
                new HashMap<>();

        response.put(
                HealthCareConstants.HOSPITAL_LIST,
                hospitals);

        return response;
    }

    @Override
    public ApiResponse<List<Hospital>> searchHospitalsByName(String name) {
        ApiResponse<List<Hospital>> response = new ApiResponse<>();

        // Edge case: empty or null search query
        if (name == null || name.trim().isEmpty()) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage("Search query cannot be empty");
            response.setData(new ArrayList<>()); // return empty list
            return response;
        }

        // Search hospitals ignoring case
        List<Hospital> hospitals = hospitalRepository.findByNameContainingIgnoreCase(name);

        // Edge case: no hospitals found
        if (hospitals.isEmpty()) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.HOSPITAL_NOT_FOUND);
            response.setData(new ArrayList<>());
        } else {
            // Hospitals found
            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage("Hospitals fetched successfully");
            response.setData(hospitals);
        }

        return response;
    }

    @Override
    public Map<String,Object> getWeeklyActivity(Long hospitalId){

        if(hospitalId == null || hospitalId <= 0){
            throw new IllegalArgumentException(
                    HealthCareConstants.INVALID_HOSPITAL_ID);
        }

        if(!hospitalRepository.existsById(hospitalId)){
            throw new RuntimeException(
                    HealthCareConstants.HOSPITAL_NOT_FOUND);
        }

        List<Map<String,Object>> dbData =
                admissionRepository.getWeeklyAdmissions(hospitalId);

        System.out.println("DB WEEKLY DATA = " + dbData);

        Map<String, Map<String,Object>> dbMap =
                new HashMap<>();

        for(Map<String,Object> row : dbData){

            String day =
                    row.get("day").toString().toUpperCase();

            Map<String,Object> formatted =
                    new HashMap<>();

            formatted.put(
                    HealthCareConstants.ADMISSIONS,
                    ((Number) row.get("admissions")).longValue()
            );

            formatted.put(
                    HealthCareConstants.DISCHARGES,
                    ((Number) row.get("discharges")).longValue()
            );

            dbMap.put(day, formatted);
        }

        List<Map<String,Object>> weekActivity =
                new ArrayList<>();

        String[] weekDays = {
                "MONDAY",
                "TUESDAY",
                "WEDNESDAY",
                "THURSDAY",
                "FRIDAY",
                "SATURDAY",
                "SUNDAY"
        };

        for(String day : weekDays){

            Map<String,Object> map =
                    new HashMap<>();

            Map<String,Object> row =
                    dbMap.get(day);

            map.put(
                    HealthCareConstants.DAY,
                    day
            );

            map.put(
                    HealthCareConstants.ADMISSIONS,
                    row != null
                            ? row.get(HealthCareConstants.ADMISSIONS)
                            : 0L
            );

            map.put(
                    HealthCareConstants.DISCHARGES,
                    row != null
                            ? row.get(HealthCareConstants.DISCHARGES)
                            : 0L
            );

            map.put(
                    HealthCareConstants.SURGERIES,
                    0L
            );

            weekActivity.add(map);
        }

        Map<String,Object> response =
                new HashMap<>();

        response.put(
                HealthCareConstants.WEEKLY_ACTIVITY,
                weekActivity
        );

        return response;
    }


    @Override
    public Map<String,Object>
    getHospitalStats(Long hospitalId){

        // Edge case
        if(hospitalId==null || hospitalId<=0){

            throw new IllegalArgumentException(
                    HealthCareConstants.INVALID_HOSPITAL_ID);
        }

        if(!hospitalRepository
                .existsById(hospitalId)){

            throw new RuntimeException(
                    HealthCareConstants.HOSPITAL_NOT_FOUND);
        }

        Map<String,Object> stats =
                hospitalRepository
                        .getHospitalStatistics(hospitalId);

        if(stats==null || stats.isEmpty()){

            throw new RuntimeException(
                    HealthCareConstants.NO_DATA_FOUND);
        }

        return stats;
    }

    @Override
    public Map<String, Object> getHospitalCredentials(Long hospitalId) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("hospitalId", hospital.getId());
        credentials.put("hospitalName", hospital.getName());
        credentials.put("email", hospital.getEmail());
        credentials.put("phone", hospital.getPhone());

        return credentials;
    }

    @Override
    public boolean resetHospitalPassword(Long hospitalId, String newPassword) {

        Optional<Hospital> optionalHospital = hospitalRepository.findById(hospitalId);

        if (optionalHospital.isEmpty()) {
            return false;
        }

        Hospital hospital = optionalHospital.get();

        hospital.setPassword(passwordEncoder.encode(newPassword));

        hospitalRepository.save(hospital);

        return true;
    }

    //rooms
    @Override
    public Map<String, Object> createRoom(Long hospitalId, String roomNumber, String roomType) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        Hospital.Room room = Hospital.Room.builder()
                .roomNumber(roomNumber)
                .roomType(roomType)
                .available(true)
                .build();

        hospital.getRooms().add(room);

        hospitalRepository.save(hospital);

        Map<String, Object> roomData = new HashMap<>();
        roomData.put("roomNumber", room.getRoomNumber());
        roomData.put("roomType", room.getRoomType());
        roomData.put("available", room.isAvailable());

        return roomData;
    }
    @Override
    public List<Map<String, Object>> getAllRooms() {

        List<Hospital> hospitals = hospitalRepository.findAll();

        List<Map<String, Object>> rooms = new ArrayList<>();

        for (Hospital hospital : hospitals) {

            for (Hospital.Room room : hospital.getRooms()) {

                Map<String, Object> roomData = new HashMap<>();

                roomData.put("hospitalId", hospital.getId());
                roomData.put("hospitalName", hospital.getName());
                roomData.put("roomNumber", room.getRoomNumber());
                roomData.put("roomType", room.getRoomType());
                roomData.put("available", room.isAvailable());

                rooms.add(roomData);
            }
        }

        return rooms;
    }

    @Override
    public Map<String, Object> getRoomByNumber(String roomNumber) {

        List<Hospital> hospitals = hospitalRepository.findAll();

        for (Hospital hospital : hospitals) {

            for (Hospital.Room room : hospital.getRooms()) {

                if (room.getRoomNumber().equals(roomNumber)) {

                    Map<String, Object> roomData = new HashMap<>();

                    roomData.put("hospitalId", hospital.getId());
                    roomData.put("hospitalName", hospital.getName());
                    roomData.put("roomNumber", room.getRoomNumber());
                    roomData.put("roomType", room.getRoomType());
                    roomData.put("available", room.isAvailable());

                    return roomData;
                }
            }
        }

        return null;
    }

    @Override
    public Map<String, Object> updateRoom(String roomNumber, String roomType, Boolean available) {

        List<Hospital> hospitals = hospitalRepository.findAll();

        for (Hospital hospital : hospitals) {

            for (Hospital.Room room : hospital.getRooms()) {

                if (room.getRoomNumber().equals(roomNumber)) {

                    if (roomType != null) {
                        room.setRoomType(roomType);
                    }

                    if (available != null) {
                        room.setAvailable(available);
                    }

                    hospitalRepository.save(hospital);

                    Map<String, Object> roomData = new HashMap<>();
                    roomData.put("hospitalId", hospital.getId());
                    roomData.put("hospitalName", hospital.getName());
                    roomData.put("roomNumber", room.getRoomNumber());
                    roomData.put("roomType", room.getRoomType());
                    roomData.put("available", room.isAvailable());

                    return roomData;
                }
            }
        }

        return null;
    }

    @Override
    public boolean deleteRoom(String roomNumber) {

        List<Hospital> hospitals = hospitalRepository.findAll();

        for (Hospital hospital : hospitals) {

            Iterator<Hospital.Room> iterator = hospital.getRooms().iterator();

            while (iterator.hasNext()) {

                Hospital.Room room = iterator.next();

                if (room.getRoomNumber().equals(roomNumber)) {

                    iterator.remove();
                    hospitalRepository.save(hospital);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public List<Map<String, Object>> getRoomsByHospital(Long hospitalId) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        List<Map<String, Object>> rooms = new ArrayList<>();

        for (Hospital.Room room : hospital.getRooms()) {

            Map<String, Object> roomData = new HashMap<>();
            roomData.put("hospitalId", hospital.getId());
            roomData.put("hospitalName", hospital.getName());
            roomData.put("roomNumber", room.getRoomNumber());
            roomData.put("roomType", room.getRoomType());
            roomData.put("available", room.isAvailable());

            rooms.add(roomData);
        }

        return rooms;
    }

    @Override
    public Map<String, Object> updateRoomStatus(String roomNumber, Boolean available) {

        List<Hospital> hospitals = hospitalRepository.findAll();

        for (Hospital hospital : hospitals) {

            for (Hospital.Room room : hospital.getRooms()) {

                if (room.getRoomNumber().equals(roomNumber)) {

                    room.setAvailable(available);

                    hospitalRepository.save(hospital);

                    Map<String, Object> roomData = new HashMap<>();
                    roomData.put("hospitalId", hospital.getId());
                    roomData.put("hospitalName", hospital.getName());
                    roomData.put("roomNumber", room.getRoomNumber());
                    roomData.put("roomType", room.getRoomType());
                    roomData.put("available", room.isAvailable());

                    return roomData;
                }
            }
        }

        return null;
    }

    @Override
    public Map<String,Object> filterHospitals(String specialization){

        if(specialization==null || specialization.trim().isEmpty())
            throw new IllegalArgumentException(HealthCareConstants.INVALID_SPECIALIZATION);

        List<Map<String,Object>> hospitals=
                hospitalRepository.filterHospitals(specialization.trim());

        if(hospitals==null || hospitals.isEmpty())
            throw new RuntimeException(HealthCareConstants.NO_HOSPITAL_FOUND);

        Map<String,Object> response=new HashMap<>();

        response.put(HealthCareConstants.HOSPITAL_LIST,hospitals);

        return response;

    }

}
