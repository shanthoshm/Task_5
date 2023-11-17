package com.banking.transaction.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WithdrawalRequest {
    private Long accountNumber;
    private Double withdrawalAmount;
}
