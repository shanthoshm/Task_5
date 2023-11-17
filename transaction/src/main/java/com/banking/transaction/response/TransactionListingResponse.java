package com.banking.transaction.response;

import com.banking.transaction.enumeration.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionListingResponse {
    private Long transactionId;
    private TransactionType transactionType;
    private Double transactionAmount;
    private Double balance;
}
