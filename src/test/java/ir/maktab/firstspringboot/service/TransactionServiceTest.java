package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.api.transaction.TransactionCreateParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void test_payOnline() {
        TransactionCreateParam createParam = TransactionCreateParam.builder()
                .customerCreditCardNumber("1111111111111111")
                .customerId(2)
                .orderId(6)
                .build();

        transactionService.payOnline(createParam);
    }
}
