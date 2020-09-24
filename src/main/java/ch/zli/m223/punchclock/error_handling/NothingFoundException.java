package ch.zli.m223.punchclock.error_handling;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NothingFoundException extends RuntimeException {
    public NothingFoundException(String message){
        super(message);
    }
}