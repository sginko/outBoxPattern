package pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.exception.MortgageOfferNotFoundException;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.model.MortgageOfferDto;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.repository.MortgageOfferRepository;

@Service
@AllArgsConstructor
public class MortgageOfferApiService {

    private final MortgageOfferRepository mortgageOfferRepository;

    public MortgageOfferDto getOffer(UUID offerId){
        return mortgageOfferRepository.findByOfferId(offerId)
            .map(e -> new MortgageOfferDto(
                e.getOfferId(),
                e.getProposedDurationInMonths(),
                e.getProposedAmount()))
            .orElseThrow(() -> new MortgageOfferNotFoundException("Nie ma takiej oferty"));
    }
}
