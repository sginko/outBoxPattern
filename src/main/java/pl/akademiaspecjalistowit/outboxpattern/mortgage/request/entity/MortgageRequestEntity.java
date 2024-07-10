package pl.akademiaspecjalistowit.outboxpattern.mortgage.request.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MortgageRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID technicalId;

    private UUID customerId;
    private Integer durationInMonths;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private RequestState state;

    private UUID offerId;

    public MortgageRequestEntity(UUID customerId, Integer durationInMonths, BigDecimal amount) {
        this.customerId = customerId;
        this.durationInMonths = durationInMonths;
        this.amount = amount;
        this.technicalId = UUID.randomUUID();
        this.state = RequestState.REQUESTED;
        this.offerId = null;
    }

    public void finalizeRequest(UUID offerId) {
        this.offerId = offerId;
        this.state = RequestState.OFFERED;
    }
}
