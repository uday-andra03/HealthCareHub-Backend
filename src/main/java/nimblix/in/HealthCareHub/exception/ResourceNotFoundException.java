package nimblix.in.HealthCareHub.exception;

public class ResourceNotFoundException
        extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

}