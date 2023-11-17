package com.banking.transaction.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WithdrawalResponse {
    private Long transactionId;
    private Double withdrawalAmount;
    private Double previousBalance;
    private Double availableBalance;
}
