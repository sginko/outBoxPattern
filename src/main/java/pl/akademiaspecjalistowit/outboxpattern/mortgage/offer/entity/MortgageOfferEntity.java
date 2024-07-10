package pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import pl.akademiaspecjalistowit.outboxpattern.customer.AccountInfo;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@DynamicUpdate
public class MortgageOfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID offerId;
    private UUID requestId;
    private Integer proposedDurationInMonths;
    private BigDecimal proposedAmount;

    private Integer counter;
    private String accountInfo;
    private Integer scoring;

    public MortgageOfferEntity(Integer proposedDurationInMonths, BigDecimal proposedAmount, UUID requestId) {
        this.offerId = UUID.randomUUID();
        this.requestId = requestId;
        this.proposedDurationInMonths = proposedDurationInMonths;
        this.proposedAmount = proposedAmount;
        counter = 0;
    }

    public void updateScoring(Integer scoring) {
        this.scoring = scoring;
        counter += 1;
    }

    public void updateAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo.value();
        counter += 1;
    }
}
