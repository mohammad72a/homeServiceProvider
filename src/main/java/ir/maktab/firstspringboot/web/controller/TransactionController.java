package ir.maktab.firstspringboot.web.controller;

import ir.maktab.firstspringboot.api.response.Response;
import ir.maktab.firstspringboot.api.transaction.TransactionCreateParam;
import ir.maktab.firstspringboot.api.transaction.TransactionCreateResult;
import ir.maktab.firstspringboot.api.transaction.TransferBalanceRequest;
import ir.maktab.firstspringboot.exception.ForbiddenException;
import ir.maktab.firstspringboot.exception.TransactionException;
import ir.maktab.firstspringboot.service.TransactionService;
import ir.maktab.firstspringboot.service.ValidateCaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final ValidateCaptchaService validateCaptchaService;
    private final RestTemplate restTemplate;

    private final static String ONLINE_PAY_URL = "http://localhost:8080/api/account/sendmoney";

    @PostMapping("/online")
    public ResponseEntity<TransactionCreateResult> payOnline(@RequestBody TransactionCreateParam createParam) {
        final boolean isValidCaptcha = validateCaptchaService.validateCaptcha(createParam.getCaptchaResponse());
        if (!isValidCaptcha) {
            throw new ForbiddenException("INVALID_CAPTCHA");
        }

        TransferBalanceRequest transferBalanceRequest = TransferBalanceRequest.builder()
                .fromAccountNumber(createParam.getCustomerCreditCardNumber())
                .toAccountNumber("1003")
                .cvv2(createParam.getCvv2())
                .expressionDate(createParam.getExpressionDate())
                .amount(createParam.getAmount())
                .build();

        Response postForObject = restTemplate.postForObject(
                ONLINE_PAY_URL,
                transferBalanceRequest,
                Response.class);

        if (postForObject == null || postForObject.getStatus().compareTo(Response.Status.OK) != 0) {
            throw new TransactionException("some error occurred. please try again");
        }

        TransactionCreateResult transactionCreateResult = transactionService.payOnline(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionCreateResult);
    }

    @PostMapping("/credit")
    public ResponseEntity<TransactionCreateResult> payWithCredit(@RequestBody TransactionCreateParam createParam) {
        TransactionCreateResult transactionCreateResult = transactionService.payWithCredit(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionCreateResult);
    }

}

