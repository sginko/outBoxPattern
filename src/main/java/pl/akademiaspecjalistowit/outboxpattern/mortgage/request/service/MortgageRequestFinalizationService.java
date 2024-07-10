package pl.akademiaspecjalistowit.outboxpattern.mortgage.request.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.model.MortgageOfferEvent;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.service.MortgageOfferEventHandler;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.entity.MortgageRequestEntity;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.exception.MortgageRequestNotFoundException;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.repository.MortgageRequestRepository;

@Slf4j
@Service
@AllArgsConstructor
public class MortgageRequestFinalizationService implements MortgageOfferEventHandler {

    private final MortgageRequestRepository mortgageRequestRepository;

    public void finalize(MortgageOfferEvent mortgageOfferEvent) {
        MortgageRequestEntity mortgageRequestEntity =
            mortgageRequestRepository.findByTechnicalId(mortgageOfferEvent.mortgageRequestId())
                .orElseThrow(() -> new MortgageRequestNotFoundException("Nie ma takiego wniosku"));

        mortgageRequestEntity.finalizeRequest(mortgageOfferEvent.offerId());
        log.info("Procesowanie wniusku {} zostało zakończone", mortgageRequestEntity);
    }

    @Override
    @Transactional
    public void handleMortgageOfferEvent(MortgageOfferEvent mortgageOfferEvent) {
        log.info("Finalization of mortgage request");
        finalize(mortgageOfferEvent);
    }
}
