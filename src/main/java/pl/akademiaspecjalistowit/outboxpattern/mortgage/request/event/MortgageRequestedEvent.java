package pl.akademiaspecjalistowit.outboxpattern.mortgage.request.event;

import java.util.UUID;

public record MortgageRequestedEvent(UUID requestId) {

}
