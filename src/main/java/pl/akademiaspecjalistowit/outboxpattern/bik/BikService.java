package pl.akademiaspecjalistowit.outboxpattern.bik;

import pl.akademiaspecjalistowit.outboxpattern.customer.AccountInfo;
import pl.akademiaspecjalistowit.outboxpattern.customer.Customer;

public interface BikService {
    AccountInfo getAccountInfo(Customer customer);
}
