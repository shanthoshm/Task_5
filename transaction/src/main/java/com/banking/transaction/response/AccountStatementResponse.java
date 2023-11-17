package com.banking.transaction.response;

import com.banking.transaction.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.admin.TransactionListing;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountStatementResponse {
    private String fullName;
    private Long accountNumber;
    private Double balance;
    private List<TransactionListingResponse> transactionListings;


}
