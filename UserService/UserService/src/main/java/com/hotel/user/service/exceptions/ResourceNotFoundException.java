package com.hotel.user.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException()
    {
        super("Resource not found on serer");
    }

    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}
