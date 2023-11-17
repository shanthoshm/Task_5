package com.banking.transaction.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferResponse {
    private Long transactionId;
    private Long fromAccount;
    private Long toAccount;
    private Double transferAmount;
    private Double previousBalance;
    private Double availableBalance;
}
