package pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.exception;

public class MortgageOfferNotFoundException extends RuntimeException{
    public MortgageOfferNotFoundException(String message) {
        super(message);
    }
}
