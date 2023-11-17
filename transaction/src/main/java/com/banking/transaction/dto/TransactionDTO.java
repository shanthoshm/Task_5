package com.banking.transaction.dto;

import com.banking.transaction.enumeration.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {
    private Long transactionId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private Long accountNumber;
    private String userName;
    private Double transactionAmount;
    private Double availableBalance;
}
