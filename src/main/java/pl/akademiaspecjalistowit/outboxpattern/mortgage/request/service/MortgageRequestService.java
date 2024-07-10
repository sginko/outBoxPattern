package pl.akademiaspecjalistowit.outboxpattern.mortgage.request.service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.dto.MortgageRequestDto;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.exception.MortgageRequestNotFoundException;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.repository.MortgageRequestRepository;

@Slf4j
@Service
@AllArgsConstructor
public class MortgageRequestService {

    private final MortgageRequestRepository mortgageRequestRepository;

    public MortgageRequestDto getMortgageRequest(UUID mortgageRequestId) {
        return mortgageRequestRepository.findByTechnicalId(mortgageRequestId)
            .map(e -> new MortgageRequestDto(e.getCustomerId(), e.getDurationInMonths(), e.getAmount()))
            .orElseThrow(() -> new MortgageRequestNotFoundException("Nie ma takiego wniosku"));
    }
}
