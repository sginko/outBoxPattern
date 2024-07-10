package pl.akademiaspecjalistowit.outboxpattern.mortgage;

import java.util.Random;
import java.util.UUID;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.outboxpattern.big.BigService;
import pl.akademiaspecjalistowit.outboxpattern.bik.BikService;
import pl.akademiaspecjalistowit.outboxpattern.br.BrService;
import pl.akademiaspecjalistowit.outboxpattern.customer.AccountInfo;
import pl.akademiaspecjalistowit.outboxpattern.customer.Customer;

@Service
public class MortgageDetailsService implements BikService, BrService, BigService {

    @SneakyThrows
    @Override
    public Integer getScoring(Customer customer) {
        Thread.sleep(20000);
        return new Random().nextInt(0,1000);
    }

    @SneakyThrows
    @Override
    public AccountInfo getAccountInfo(Customer customer) {
        Thread.sleep(20000);
        return new AccountInfo("AccInfo");
    }

    @SneakyThrows
    @Override
    public boolean isCustomerAllowed(AccountInfo customer, Integer scoring) {
        Thread.sleep(5000);
        return true;
    }

}
