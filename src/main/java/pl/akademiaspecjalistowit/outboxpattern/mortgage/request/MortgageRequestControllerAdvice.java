package pl.akademiaspecjalistowit.outboxpattern.mortgage.request;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.exception.MortgageRequestNotFoundException;

@ControllerAdvice
public class MortgageRequestControllerAdvice {

    @ExceptionHandler(MortgageRequestNotFoundException.class)
    public ResponseEntity<String> handleMortgageRequestNotFoundException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
