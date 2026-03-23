package nimblix.in.HealthCareHub.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(String s) {
        super("Room not found");
    }
}