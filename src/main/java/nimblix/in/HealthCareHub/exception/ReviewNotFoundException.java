package nimblix.in.HealthCareHub.exception;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException() {
        super("No reviews available for this doctor"); // default message
    }
    public ReviewNotFoundException(String message) {
        super(message);
    }

}
