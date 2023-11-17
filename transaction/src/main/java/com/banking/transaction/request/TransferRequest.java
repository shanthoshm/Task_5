package com.banking.transaction.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferRequest {
    private Long fromAccountNumber;
    private Long toAccountNumber;
    private Double transferAmount;
}
