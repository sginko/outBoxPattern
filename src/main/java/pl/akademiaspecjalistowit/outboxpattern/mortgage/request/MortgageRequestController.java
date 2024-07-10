package pl.akademiaspecjalistowit.outboxpattern.mortgage.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.dto.MortgageRequestDto;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.dto.MortgageRequestInfoDto;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.service.MortgageRequestApiService;

@RestController
@Slf4j
@RequestMapping("/mortgage/request")
@AllArgsConstructor
public class MortgageRequestController {

    private final MortgageRequestApiService mortgageRequestService;

    @PostMapping
    public UUID mortgageRequest(@RequestBody MortgageRequestDto mortgageRequestDto) {
        log.info("Mortgage request received.");
        return mortgageRequestService.receiveRequest(mortgageRequestDto);
    }

    @GetMapping("/{requestId}")
    public MortgageRequestInfoDto getMortgageRequestInfo(@PathVariable("requestId") UUID requestId) {
        log.info("GetMortgageRequestInfo received.");
        return mortgageRequestService.getRequestProcessingInfo(requestId);
    }
}
