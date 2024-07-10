package pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.model;

import java.math.BigDecimal;
import java.util.UUID;

public record MortgageOfferDto(UUID offerId,
                               Integer proposedDurationInMonths,
                               BigDecimal proposedAmount) {
}
