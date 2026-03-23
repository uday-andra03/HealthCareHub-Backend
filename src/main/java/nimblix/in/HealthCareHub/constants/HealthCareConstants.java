package nimblix.in.HealthCareHub.constants;

public class HealthCareConstants {

    // ─── Subscription / Trial ────────────────────────────────────────────────
    public static final String TRAIL_END_DATE                   = "Trial end date";
    public static final String TRAIL_START_DATE                 = "Trial start date";
    public static final String DAYS_REMAINING                   = "Days remaining";
    public static final String SUBSCRIPTION_PAID                = "Subscription paid";
    public static final int    PAID_PLAN_DAYS                   = 30;
    public static final String DOCTORS_FETCHED_SUCCESS ="Doctors fetched successfully" ;
    public static       String SUBSCRIPTION_ACTIVATED_SUCCESSFULLY = "Subscription activated successfully";
    public static       String SUBSCRIPTION_EXPIRED             = "Subscription expired";
    public static       String SUBSCRIPTION_STATUS              = "Subscription status";
    public static       String SUBSCRIPTION_TRAIL               = "TRIAL";
    public static       String PAID                             = "PAID";


    // Hospital Subscription Messages
    public static final String NO_ACTIVE_HOSPITAL_SUBSCRIPTIONS =
            "No hospitals with active subscriptions found";

    public static final String HOSPITALS_WITH_SUBSCRIPTIONS_FETCHED_SUCCESS =
            "Hospitals with subscriptions fetched successfully";

    public static final String HOSPITAL_ID_REQUIRED =
            "Hospital ID is required";


    public static final String SUBSCRIPTION_ASSIGNED_SUCCESS =
            "Subscription assigned successfully";


    public static final String SUBSCRIPTION_NOT_FOUND_FOR_HOSPITAL =
            "Subscription not found for hospital";

    public static final String SUBSCRIPTION_RENEWED_SUCCESS =
            "Subscription renewed successfully";

    public static final String INVALID_PAYMENT_REQUEST =
            "Invalid payment request";

    public static final String SUBSCRIPTION_PAYMENT_FAILED =
            "Subscription payment failed";

    public static final String SUBSCRIPTION_PAYMENT_SUCCESS =
            "Subscription payment successful";

    public static final String SUBSCRIPTION = "subscription";

    public static final String SUBSCRIPTION_UPDATED_SUCCESSFULLY =
            "Subscription plan updated successfully";

    public static final String SUBSCRIPTION_NOT_FOUND =
            "Subscription not found";

    public static final String SUBSCRIPTION_UPGRADED_SUCCESSFULLY =
            "Subscription upgraded successfully";

    public static final String SUBSCRIPTION_DOWNGRADED_SUCCESSFULLY =
            "Subscription downgraded successfully";

    public static final String SUBSCRIPTION_EXTENDED_SUCCESSFULLY =
            "Subscription extended successfully";

    public static final String SUBSCRIPTION_HISTORY_FETCHED_SUCCESSFULLY =
            "Subscription history fetched successfully";

    public static final String SUBSCRIPTION_HISTORY_NOT_FOUND =
            "No subscription history found";

    public static final String INVALID_HOSPITAL_ID = "Invalid hospital id";

    public static final String SUBSCRIPTION_STATUS_FETCHED =
            "Subscription status fetched successfully";

    public static final String SUBSCRIPTION_ACTIVE = "ACTIVE";


    public static final String SUBSCRIPTION_CANCELLED = "CANCELLED";

    public static final String SUBSCRIPTIONS_EXPIRING_SOON =
            "Subscriptions expiring soon fetched successfully";
    public static final String SUBSCRIPTION_REMINDER_SENT =
            "Subscription reminder sent successfully";


    public static final String SUBSCRIPTION_ALREADY_EXPIRED =
            "Subscription already expired";

    public static final String SUBSCRIPTION_SUSPENDED =
            "Subscription suspended successfully";

    public static final String SUBSCRIPTION_ALREADY_SUSPENDED =
            "Subscription is already suspended";

    public static final String SUBSCRIPTION_REACTIVATED =
            "Subscription reactivated successfully";

    public static final String SUBSCRIPTION_NOT_SUSPENDED =
            "Subscription is not suspended";


    // ─── Roles ───────────────────────────────────────────────────────────────
    public static final String ADMIN                            = "ADMIN";
    public static final String ADMIN_ROLE                       = "ADMIN";
    public static final String TEACHER                          = "TEACHER";
    public static       String TEACHER_ROLE                     = "TEACHER";
    public static final String STUDENT                          = "STUDENT";
    public static final String PARENT                           = "PARENT";
    public static       String ROLE_MISMATCH                    = "Role mismatch";

    // ─── Status values ───────────────────────────────────────────────────────
    public static final String STATUS_ERORR                     = "erorr";
    public static       String STATUS_SUCCESS                   = "SUCCESS";
    public static       String STATUS_FAILURE                   = "FAILED";
    public static final String STATUS_FAILED                    = "FAILED";
    public static       String STATUS                           = "status";
    public static       String ACTIVE                           = "active";
    public static       String IN_ACTIVE                        = "inActive";
    public static       String PENDING                          = "PENDING";
    public static       String FAILED                           = "FAILED";
    public static final String SUCCESS                          = "Success";
    public static final String FAILURE                          = "Failure";

    // ─── Generic keys / labels ───────────────────────────────────────────────
    public static       String DATA                             = "Data";
    public static final String DATA_KEY                         = "data";
    public static       String MESSAGE                          = "message: ";
    public static final String COUNT                            = "Count";
    public static final String NOT_FOUND                        = "NOT_FOUND";

    // ─── Assignment ──────────────────────────────────────────────────────────
    public static final String ASSIGNMENT_CREATED_SUCCESSFULLY  = "Assignment created successfully";
    public static final String ASSIGNMENT_ID                    = "assignmentId";

    // ─── Login / Auth ────────────────────────────────────────────────────────
    public static final String LOGIN_FAILED                     = "Login failed";
    public static final String LOGIN_SUCCESS                    = "Login successful";
    public static final String INVALID_CREDENTIALS              = "Invalid credentials";
    public static final String EMAIL_REQUIRED                   = "Email is required";
    public static final String PASSWORD_REQUIRED                = "Password is required";
    public static final String PASSWORD_MISMATCH                = "Passwords do not match";
    public static final String INVALID_EMAIL_FORMAT             = "Invalid email format";

    // ─── User ────────────────────────────────────────────────────────────────
    public static       String USER_NOT_FOUND                   = "user not found: ";
    public static final Object USER_REGISTRATION_SUCCESSFULLY   = "User Registered Successfully";

    // ─── Student ─────────────────────────────────────────────────────────────
    public static       String STUDENT_NOT_FOUND                = "Student not found";

    // ─── Attendance ──────────────────────────────────────────────────────────
    public static final String PRESENT                          = "PRESENT";
    public static final String ABSENT                           = "ABSENT";
    public static final String TARDY                            = "TARDY";

    // ─── Schools ─────────────────────────────────────────────────────────────
    public static       String SCHOOLS_LIST                     = "Schools list";

    // ─── Doctor ──────────────────────────────────────────────────────────────
    public static final String DOCTOR_REGISTERED_SUCCESS        = "Doctor Registered Successfully";
    public static final String DOCTOR_ALREADY_EXISTS            = "Doctor already exists with this email";
    public static final String DOCTOR_EMAIL_EXISTS              = "Doctor Email exists with this email id";
    public static final String DOCTOR_PHONE_EXISTS              = "Doctor Phone exists with this phone number";
    public static final String DOCTOR_NOT_FOUND                 = "Doctor not found";
    public static       String DOCTOR_NOT_FOUND_WITH_ID         = "Doctor not found with id";
    public static final String DOCTOR_FETCHED_SUCCESSFULLY      = "Doctor Fetched Successfully";
    public static final String DOCTOR_PROFILE_FETCHED_SUCCESSFULLY = "Doctor Profile Fetched Successfully";
    public static final String DOCTOR_REVIEWS_FETCHED_SUCCESSFULLY = "DOCTOR REVIEWS FETCHED SUCCESSFULLY";
    public static final String DOCTORS_FETCHED_SUCCESSFULLY     = "Doctors fetched successfully";
    public static final Object DOCTOR_ADDED_SUCCESS             = "Doctor added successfully";
    public static final String DOCTOR_IS_INACTIVE               = "Doctor is Inactive";
    public static final String DOCTOR_ID_CANNOT_BE_NULL_ZERO_OR_NEGATIVE = "Doctor Id cannot be null or negative";
    public static       String INVALID_DOCTOR_ID                = "Invalid doctor id";
    public static       String INVALID_DOCTOR_ID_IT_MUST_BE_A_NUMBER = "Invalid doctorId. It must be a number.";
    public static final String INVALID_DOCTOR_NAME              = "Doctor name cannot be empty";
    public static final String DOCTOR_NOT_BELONG_TO_HOSPITAL    = "Doctor not belong to hospital";
    public static final String DOCTORS                          = "doctors";

    // ─── Doctor status ───────────────────────────────────────────────────────
    public static final String DOCTOR_STATUS_AVAILABLE          = "AVAILABLE";
    public static final String DOCTOR_STATUS_IN_OPERATION       = "IN_OPERATION";
    public static final String DOCTOR_STATUS_ON_BREAK           = "ON_BREAK";
    public static final String DOCTOR_STATUS_ON_LEAVE           = "ON_LEAVE";
    public static final String DOCTOR_STATUS_BUSY               = "BUSY";
    public static final String DOCTOR_STATUS_OFF_DUTY           = "OFF_DUTY";
    public static final String DOCTOR_STATUS_UPDATED_SUCCESSFULLY = "Doctor status updated successfully";

    // ─── Doctor sign-in / sign-out ───────────────────────────────────────────
    public static final String DOCTOR_INVALID_CREDENTIALS       = "Invalid doctor credentials";
    public static final String DOCTOR_ACCOUNT_DISABLED          = "Doctor account is disabled";
    public static final String DOCTOR_SIGNED_IN                 = "Doctor signed in successfully";
    public static final String DOCTOR_SIGN_IN_SUCCESS           = "Doctor sign in successful";
    public static final String DOCTOR_SIGNED_OUT                = "Doctor signed out successfully";
    public static final String DOCTOR_SIGN_OUT_SUCCESS          = "Doctor sign out successful";

    // ─── Doctor schedule ─────────────────────────────────────────────────────
    public static       String DOCTORSCHEDULESUCCESSFULLY       = "Doctor Schedule created successfully";
    public static       String SCHEDULESTATUSUPDATED            = "Schedule status updated successfully";
    public static final String DOCTOR_SCHEDULE_CREATED_SUCCESSFULLY = "Doctor schedule created successfully";
    public static final String DOCTOR_NOT_FOUND_OR_SCHEDULE_CREATION_FAILED = "Doctor not found or schedule creation failed";
    public static final String NO_SCHEDULES_FOUND_FOR_DOCTOR    = "No schedules found for doctor with id: ";
    public static final Object DOCTOR_AVAILABILITY_FETCHED_SUCCESSFULLY = "Doctor availability fetched successfully";
    public static final String STATUS_CANNOT_BE_NULL_OR_EMPTY   = "Status cannot be null or empty";

    // ─── Schedule statuses ───────────────────────────────────────────────────
    public static final String SCHEDULED                        = "SCHEDULED";
    public static final String COMPLETED                        = "COMPLETED";
    public static final String CANCELLED                        = "CANCELLED";
    public static final String ONGOING                          = "ONGOING";

    // ─── Hospital ────────────────────────────────────────────────────────────
    public static final String HOSPITAL_NOT_FOUND               = "Hospital not found";
    public static final String NO_HOSPITAL_FOUND                = "No hospital found";
    public static final String NO_HOSPITALS_FOUND               = "No hospitals found";
    public static final String HOSPITAL_ALREADY_EXISTS          = "Hospital already exists at this location";
    public static final String HOSPITAL_REGISTIRED_SUCCESSFULLY = "Hospital Registered Successfully";
    public static final String REGISTER_SUCCESS                 = "Registration successful";
    public static final String HOSPITAL_FETCHED_SUCCESSFULLY    = "Hospital fetched successfully";
    public static final String HOSPITAL_FETCHED_SUCCESS         = "Hospital fetched successfully";
    public static final String HOSPITAL_LIST_FETCHED_SUCCESS    = "Hospital list fetched successfully";
    public static final String HOSPITAL_OVERVIEW_FETCHED_SUCCESS = "Hospital overview fetched successfully";
    public static final String HOSPITAL_STATS_FETCHED           = "Hospital stats fetched successfully";
    public static final String HOSPITAL_STATS_FETCHED_SUCCESS   = "Hospital statistics fetched successfully";
    public static final String HOSPITAL_SORT_SUCCESS            = "Hospital sorted successfully";
    public static final String HOSPITAL_SORT_ERROR              = "Invalid sort parameter. Allowed values are 'asc' or 'desc'.";
    public static final String HOSPITALS_SORTED_SUCCESS         = "Hospitals sorted successfully";

    public static final String HOSPITAL_ID_NULL                 = "Hospital id cannot be null";
    public static final String HOSPITAL_NAME_EMPTY              = "Hospital name cannot be empty";
    public static final String HOSPITAL_NAME_REQUIRED           = "Hospital name is required";
    public static final String EMAIL_ALREADY_EXISTS             = "Hospital email already registered";
    public static final String REVIEWS_FETCHED_SUCCESS          = "Reviews fetched successfully";
    public static final String HOSPITAL_OVERVIEW                = "Hospital overview";
    public static final String HOSPITAL_LIST                    = "Hospital list";

    // ─── Hospital stats keys ─────────────────────────────────────────────────
    public static final String SPECIALIZATION_DISTRIBUTION      = "Specialization distribution";
    public static final String ADMISSION_ACTIVITY               = "admissionActivity";
    public static final String SURGERY_ACTIVITY                 = "surgeryActivity";
    public static final String WEEKLY_ACTIVITY                  = "Weekly activity";
    public static final String DAY                              = "Day";
    public static final String ADMISSIONS                       = "Admissions";
    public static final String DISCHARGES                       = "Discharges";
    public static final String SURGERIES                        = "Surgeries";

    // ─── Specialization ──────────────────────────────────────────────────────
    public static final String SPECIALIZATION_NOT_FOUND         = "Specialization not found";
    public static final String SPECIALIZATION_FETCHED_SUCCESS   = "Specialization distribution fetched successfully";
    public static final String NO_SPECIALIZATION_FOUND          = "No specialization found";
    public static final String INVALID_SPECIALIZATION           = "Specialization is required";
    public static final String SPECIALIZATION_REQUIRED          = "Specialization parameter is required";

    // ─── Sort parameters ─────────────────────────────────────────────────────
    public static final String SORT_RATING                      = "rating";
    public static final String SORT_NAME                        = "name";
    public static final String SORT_DOCTOR_COUNT                = "doctorcount";
    public static final String INVALID_SORT_PARAMETER           = "Invalid sort parameter";

    // ─── Patient ─────────────────────────────────────────────────────────────
    public static       String PATIENT_REGISTERED_SUCCESSFULLY  = "Patient Registered Successfully";
    public static final String PATIENT_DELETED_SUCCESSFULLY     = "Patient Deleted Successfully";
    public static final Object PATIENT_ADMITTED_SUCCESSFULLY    = "Patient Admitted Successfully";
    public static       String PATIENT_NOT_FOUND_WITH_ID        = "Patient not found with id";
    public static final String PATIENTNOTFOUND                  = "Patient not found with id: ";
    public static       String INVALID_PATIENT_ID               = "Invalid patient id";
    public static       String PATIENT_RECORDS                  = "Patient Records";
    public static       String PATIENT_RECORDS_FETCHED_SUCCESSFULLY = "Patient records fetched successfully";
    public static       String NO_PATIENT_HISTORY_FOUND         = "No patient history found";

    // ─── Medical / Lab ───────────────────────────────────────────────────────
    public static final String MEDICAL_HISTORY_FETCHED_SUCCESSFULLY = "Medical History Fetched Successfully";
    public static final Object LAB_RESULT_FETCHED_SUCCESSFULLY  = "Lab Result Fetched Successfully";
    public static final String FETCH_SUCCESS                    = "Record Fetched Successfully";
    public static final String FETCH_FAILURE                    = "Failed to Fetch the record";

    // ─── Prescription ────────────────────────────────────────────────────────
    public static       String PRESCRIPTION_ADDED_SUCCESSFULLY  = "Prescription added successfully";
    public static final String FETCHED_SUCCESSFULY              = "SUCCESSFULLY FETCHED PRESCRIPTION";
    public static final String FETCH_FAILED                     = "FAILED TO FETCHED PRESCRIPTION";

    // ─── Rooms ───────────────────────────────────────────────────────────────
    public static final String ROOMS_ADDED_SUCCESSFULLY         = "Rooms Added Successfully";
    public static final String ROOMS_FETCHED_SUCCESSFULLY       = "Rooms Fetched Successfully";

    // ─── OTP ─────────────────────────────────────────────────────────────────
    public static final String INVALID_OTP                      = "Invalid OTP";
    public static final String OTP_VERIFIED                     = "OTP Verified successfully";
    public static final String REGISTRATION_FAILED              = "Registration failed";

    // ─── Revenue ─────────────────────────────────────────────────────────────
    public static final String DAILY_REVENUE_FETCHED            = "Daily revenue fetched successfully";
    public static final String MONTHLY_REVENUE_FETCHED          = "Monthly revenue fetched successfully";
    public static final String YEARLY_REVENUE_FETCHED           = "Yearly revenue fetched successfully";
    public static final String REVENUE_SUMMARY_FETCHED          = "Revenue summary fetched successfully";

    // ─── Dashboard / Activity ────────────────────────────────────────────────
    public static final String DASHBOARD_FETCHED_SUCCESS        = "Dashboard fetched successfully";
    public static final String ACTIVITY_FETCHED_SUCCESS         = "Activity fetched successfully";
    public static final String NO_ACTIVITY_FOUND                = "No activity found";
    public static final String WEEKLY_ACTIVITY_FETCHED          = "Weekly activity fetched successfully";
    public static final String NO_DOCTORS_FOUND                 = "No doctors found for the given hospital";

    // ─── Validation / Request ────────────────────────────────────────────────
    public static final String INVALID_REQUEST                  = "Invalid request";
    public static final String REQUEST_BODY_NULL                = "Request body cannot be null";
    public static       String REQUEST_BODY_IS_MISSING_OR_CONTAINS_INVALID_DATA = "Required body is missing or contain invalid data";
    public static final String INVALID_TOTAL_BEDS               = "Total beds must be greater than zero";
    public static final String INVALID_ESTABLISHED_YEAR         = "Established year is invalid";
    public static final String PHONE_REQUIRED                   = "Phone number is required";
    public static       String BAD_REQUEST                      = "BAD REQUEST";

    // ─── Errors / Misc ───────────────────────────────────────────────────────
    public static final String SOMETHING_WENT_WRONG             = "Something went wrong";
    public static final String INTERNAL_SERVER_ERROR            = "Internal Server Error";
    public static final String NO_DATA_FOUND                    = "No data found";
    public static final String TOTALREVIEWS                     = "totalReviews";
    public static final String AVERAGERATING                    = "averageRate";

    // ─── Admin Controller ───────────────────────────────────────────────────────
    public static final String PAYMENT_HISTORY_NOT_FOUND = "No payment history found";
    public static final String PAYMENT_HISTORY_FETCHED_SUCCESS = "Payment history fetched successfully";
    public static final String HOSPITAL_CREDENTIALS = "hospitalCredentials";
    public static final String HOSPITAL_CREDENTIALS_FETCHED_SUCCESS = "Hospital credentials fetched successfully";
    public static final String NOTIFICATIONS = "notifications";
    public static final String NOTIFICATIONS_NOT_FOUND = "No notifications found";
    public static final String NOTIFICATIONS_FETCHED_SUCCESS = "Notifications fetched successfully";
    public static final String INVALID_NOTIFICATION_REQUEST = "Invalid notification request";
    public static final String NOTIFICATION_SENT_SUCCESSFULLY = "Notification sent successfully";
    public static final String NOTIFICATION = "notification";
    public static final String INVALID_PASSWORD_REQUEST = "Invalid password reset request";
    public static final String HOSPITAL_PASSWORD_RESET_SUCCESS = "Hospital password reset successfully";


    //--------------------Room-------------------------//
    public static final String INVALID_ROOM_REQUEST =
            "Invalid room request";

    public static final String ROOM_CREATED_SUCCESS =
            "Room created successfully";

    public static final String ROOM = "room";

    public static final String ROOMS = "rooms";



    public static final String ROOMS_NOT_FOUND =
            "No rooms found";

    public static final String ROOM_FETCHED_SUCCESSFULLY =
            "Room fetched successfully";

    public static final String ROOM_NOT_FOUND =
            "Room not found";

    public static final String ROOM_UPDATED_SUCCESSFULLY =
            "Room updated successfully";

    public static final String ROOM_DELETED_SUCCESSFULLY =
            "Room deleted successfully";

    public static final String INVALID_ROOM_STATUS_REQUEST =
            "Invalid room status request";

    public static final String ROOM_STATUS_UPDATED_SUCCESSFULLY =
            "Room status updated successfully";

    public static final String NO_USERS_FOUND = "No users found";
    public static final String USERS = "users";
    public static final String USERS_FETCHED_SUCCESS = "Users fetched successfully";

    public static final String USER_FETCHED_SUCCESS =
            "User fetched successfully";

    public static final String INVALID_USER_ID =
            "Invalid user id";

    public static final String DOCTOR_APPROVED="Doctor approved successfully";

    public static final String DOCTOR_REJECTED="Doctor rejected successfully";
    public static final String DOCTOR_DELETED="Doctor deleted successfully";
    public static final String HOSPITAL_APPROVED="Hospital approved successfully";
    public static final String  HOSPITAL_DELETED="Hospital deactivated successfully";
    public static final String PATIENTS_FETCHED="Patients fetched successfully";
    public static final String NO_PATIENT_FOUND="No patients found";
    public static final String PATIENTS="patients";
    public static final String PATIENT_FETCHED="Patient fetched successfully";
    public static final String PATIENT_NOT_FOUND="Patient not found";

    public static final String PATIENT_DELETED="Patient deleted successfully";
    public static final String SPECIALIZATION_ADDED="Specialization added successfully";
    public static final String SPECIALIZATIONS_FETCHED="Specializations fetched successfully";
    public static final String SPECIALIZATIONS="specializations";
    public static final String SPECIALIZATION_DELETED="Specialization deleted successfully";
    public static final String INVALID_SPECIALIZATION_ID="Invalid specialization id";

    public static final String DOCTOR_ALREADY_REJECTED="Doctor already rejected";

    public static final String DOCTOR_STATUS_REJECTED="REJECTED";

    public static final String DOCTOR_ALREADY_APPROVED="Doctor already approved";

    public static final String DOCTOR_STATUS_APPROVED="APPROVED";

    public static final String INVALID_DOCTOR_ID_FORMAT="Invalid doctor ID format";






}