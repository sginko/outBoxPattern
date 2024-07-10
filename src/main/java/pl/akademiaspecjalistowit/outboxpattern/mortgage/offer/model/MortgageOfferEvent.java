package pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.model;

import java.util.UUID;

public record MortgageOfferEvent(UUID offerId, UUID mortgageRequestId) {
}
