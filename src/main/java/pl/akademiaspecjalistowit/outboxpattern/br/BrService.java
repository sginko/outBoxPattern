package pl.akademiaspecjalistowit.outboxpattern.br;

import pl.akademiaspecjalistowit.outboxpattern.customer.AccountInfo;

public interface BrService {
    boolean isCustomerAllowed(AccountInfo customer, Integer scoring);
}
