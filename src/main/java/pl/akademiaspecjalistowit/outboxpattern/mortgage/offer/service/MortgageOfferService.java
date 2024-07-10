package pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.spring.annotations.Recurring;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akademiaspecjalistowit.outboxpattern.customer.AccountInfo;
import pl.akademiaspecjalistowit.outboxpattern.customer.Customer;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.MortgageDetailsService;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.entity.MortgageOfferEntity;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.model.MortgageOfferEvent;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.repository.MortgageOfferRepository;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.dto.MortgageRequestDto;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.event.MortgageRequestedEvent;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.event.MortgageRequestedEventHandler;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.service.MortgageRequestService;

@Slf4j
@Service
@AllArgsConstructor
public class MortgageOfferService implements MortgageRequestedEventHandler {

    private final JobScheduler jobScheduler;
    private final MortgageRequestService mortgageRequestService;
    private final MortgageDetailsService mortgageDetailsService;
    private final MortgageOfferRepository mortgageOfferRepository;
    private final MortgageOfferEventHandler mortgageOfferEventHandler;


    @Recurring(id = "mortgage-finalization-offer-job", interval = "PT5S")
    @Job(name = "Mortgage-finalization-offer-job")
    public void finalizeOffer() {
        //3 (1,2)
        log.info("Offer finalization attempt");

        List<MortgageOfferEntity> mortgageOfferEntites =
            mortgageOfferRepository.findByScoringNotNullAndAccountInfoNotNull();

        if (mortgageOfferEntites.isEmpty()) {
            log.info("No offer is not ready for finalization");
            return;
        } else {
            log.info("offer finalization begins!");
        }

        mortgageOfferEntites.forEach( e -> {
            boolean customerAllowed = mortgageDetailsService.isCustomerAllowed(
                new AccountInfo(e.getAccountInfo()),
                e.getScoring());

            if (!customerAllowed) {
                log.info("Scoring for account was to low. No mortgage");
                return;
            }

            log.info("Mortgage request processed. Offer {} is ready", e);
            mortgageOfferEventHandler
                .handleMortgageOfferEvent(new MortgageOfferEvent(e.getOfferId(), e.getRequestId()));
        });

    }

    private Customer getCustomer(UUID customerId) {
        return new Customer();
    }

    @Override
    @Transactional
    public void handleMortgageRequestedEvent(MortgageRequestedEvent mortgageRequestedEvent) {
        MortgageRequestDto mortgageRequestDto =
            mortgageRequestService.getMortgageRequest(mortgageRequestedEvent.requestId());
        MortgageOfferEntity mortgageOfferEntity =
            new MortgageOfferEntity(mortgageRequestDto.durationInMonths(), mortgageRequestDto.amount(),
                mortgageRequestedEvent.requestId());
        mortgageOfferRepository.save(mortgageOfferEntity);

        jobScheduler
            .enqueue(() -> prepareScoring(mortgageRequestedEvent.requestId(), mortgageOfferEntity.getOfferId()));
        log.info("Mortgage offer scoring scheduled");

        jobScheduler
            .enqueue(() -> prepareAccountInfo(mortgageRequestedEvent.requestId(), mortgageOfferEntity.getOfferId()));
        log.info("Mortgage offer get account info scheduled");

    }

    @Transactional
    public void prepareAccountInfo(UUID mortgageRequestId, UUID offerId) {
        //2
        MortgageRequestDto mortgageRequestDto = mortgageRequestService.getMortgageRequest(mortgageRequestId);
        Customer customer = getCustomer(mortgageRequestDto.customerId());
        AccountInfo accountInfo = mortgageDetailsService.getAccountInfo(customer);
        log.info("Customer {} account info prepared  {}", mortgageRequestDto.customerId(), accountInfo);

        MortgageOfferEntity mortgageOfferEntity = mortgageOfferRepository.findByOfferId(offerId).orElseThrow();
        mortgageOfferEntity.updateAccountInfo(accountInfo);
    }

    @Transactional
    public void prepareScoring(UUID mortgageRequestId, UUID offerId) {
        //1
        MortgageRequestDto mortgageRequestDto = mortgageRequestService.getMortgageRequest(mortgageRequestId);
        Customer customer = getCustomer(mortgageRequestDto.customerId());
        Integer scoring = mortgageDetailsService.getScoring(customer);
        log.info("Customer {} received scoring {}", mortgageRequestDto.customerId(), scoring);
        MortgageOfferEntity mortgageOfferEntity = mortgageOfferRepository.findByOfferId(offerId).orElseThrow();
        mortgageOfferEntity.updateScoring(scoring);
    }
}
