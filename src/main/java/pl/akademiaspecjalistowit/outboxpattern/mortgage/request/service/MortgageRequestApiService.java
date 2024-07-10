package pl.akademiaspecjalistowit.outboxpattern.mortgage.request.service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.dto.MortgageRequestDto;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.dto.MortgageRequestInfoDto;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.entity.MortgageRequestEntity;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.event.MortgageRequestedEvent;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.event.MortgageRequestedEventHandler;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.exception.MortgageRequestNotFoundException;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.repository.MortgageRequestRepository;

@Slf4j
@Service
@AllArgsConstructor
public class MortgageRequestApiService {

    private final MortgageRequestRepository mortgageRequestRepository;
    private final MortgageRequestedEventHandler mortgageRequestedEventHandler;

    public UUID receiveRequest(MortgageRequestDto mortgageRequestDto) {
        MortgageRequestEntity mortgageRequestEntity = new MortgageRequestEntity(mortgageRequestDto.customerId(),
            mortgageRequestDto.durationInMonths(),
            mortgageRequestDto.amount());
        mortgageRequestRepository.save(mortgageRequestEntity);
        mortgageRequestedEventHandler
            .handleMortgageRequestedEvent(
                new MortgageRequestedEvent(mortgageRequestEntity.getTechnicalId()));
        return mortgageRequestEntity.getTechnicalId();
    }

    public MortgageRequestInfoDto getRequestProcessingInfo(UUID requestId) {
        return mortgageRequestRepository.findByTechnicalId(requestId)
            .map(e -> new MortgageRequestInfoDto(e.getState(), e.getOfferId()))
            .orElseThrow(() -> new MortgageRequestNotFoundException("Nie ma takiego wniosku"));
    }
}
