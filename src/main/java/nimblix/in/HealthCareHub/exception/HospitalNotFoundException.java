package nimblix.in.HealthCareHub.exception;

public class HospitalNotFoundException extends RuntimeException {

    public HospitalNotFoundException(Long id) {
        super("Hospital not found with id: " + id);
    }

    public HospitalNotFoundException(String message) {
        super(message);
    }
}
