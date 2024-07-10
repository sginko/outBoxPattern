package pl.akademiaspecjalistowit.outboxpattern.mortgage.request.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record MortgageRequestDto(UUID customerId, Integer durationInMonths, BigDecimal amount) {
}
