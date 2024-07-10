package pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.service;

import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.model.MortgageOfferEvent;

public interface MortgageOfferEventHandler {
    void handleMortgageOfferEvent(MortgageOfferEvent mortgageOfferEvent);
}
