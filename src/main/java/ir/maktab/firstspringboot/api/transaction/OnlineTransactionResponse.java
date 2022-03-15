package ir.maktab.firstspringboot.api.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OnlineTransactionResponse {
    private Long transactionId;

    private String accountNumber;

    private Double transactionAmount;

    private Timestamp transactionDateTime;

    public OnlineTransactionResponse(String accountNumber, Double transactionAmount, Timestamp transactionDateTime) {
        this.accountNumber = accountNumber;
        this.transactionAmount = transactionAmount;
        this.transactionDateTime = transactionDateTime;
    }
}
