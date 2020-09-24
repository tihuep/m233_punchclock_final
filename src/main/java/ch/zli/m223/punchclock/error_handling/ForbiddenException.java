package ch.zli.m223.punchclock.error_handling;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message){
        super(message);
    }
}