package pl.akademiaspecjalistowit.outboxpattern.mortgage.request.event;

public interface MortgageRequestedEventHandler {

    void handleMortgageRequestedEvent(MortgageRequestedEvent mortgageRequestedEvent);
}
