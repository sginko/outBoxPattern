package pl.akademiaspecjalistowit.outboxpattern.mortgage.offer;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.model.MortgageOfferDto;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.offer.service.MortgageOfferApiService;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/mortgage/offer")
public class MortgageOfferController {

    private final MortgageOfferApiService mortgageOfferService;

    @GetMapping("/{offerId}")
    public MortgageOfferDto getMortgageOffer(@PathVariable("offerId") UUID offerId) {
        log.info("GetMortgageRequestInfo received.");
        return mortgageOfferService.getOffer(offerId);
    }
}
