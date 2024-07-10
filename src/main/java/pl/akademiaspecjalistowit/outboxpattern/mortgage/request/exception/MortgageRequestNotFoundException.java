package pl.akademiaspecjalistowit.outboxpattern.mortgage.request.exception;

public class MortgageRequestNotFoundException extends RuntimeException {
    public MortgageRequestNotFoundException(String message) {
        super(message);
    }
}
