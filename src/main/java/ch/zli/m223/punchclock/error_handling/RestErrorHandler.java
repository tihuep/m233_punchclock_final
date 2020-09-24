package ch.zli.m223.punchclock.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Object processValidationError(ForbiddenException ex) {
        String result = ex.getMessage();
        System.out.println("###########"+result);
        return ex;
    }

    @ExceptionHandler(NothingFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public Object processValidationError(NothingFoundException ex) {
        String result = ex.getMessage();
        System.out.println("###########"+result);
        return ex;
    }
}
