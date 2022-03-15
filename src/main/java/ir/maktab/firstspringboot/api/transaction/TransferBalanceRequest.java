package ir.maktab.firstspringboot.api.transaction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferBalanceRequest {
    private String fromAccountNumber;
    private String cvv2;
    private String expressionDate;
    private String toAccountNumber;
    private Double amount;
}

