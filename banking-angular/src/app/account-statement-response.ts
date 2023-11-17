import { TransactionListingResponse } from "./transaction-listing-response";

export class AccountStatementResponse {
    fullName!: string;
    accountNumber!: number;
    balance!: number;
    transactionListings!: TransactionListingResponse[];
}
