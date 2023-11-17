package com.banking.transaction.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepositResponse {
    private Long transactionId;
    private Double depositedAmount;
    private Double previousBalance;
    private Double availableBalance;
}
