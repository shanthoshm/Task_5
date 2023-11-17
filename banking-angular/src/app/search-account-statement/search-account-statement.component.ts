import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TransactionServiceService } from '../transaction-service.service';
import { Transaction } from '../transaction';
import { AccountStatementResponse } from '../account-statement-response';

@Component({
  selector: 'app-search-account-statement',
  templateUrl: './search-account-statement.component.html',
  styleUrls: ['./search-account-statement.component.css']
})

export class SearchAccountStatementComponent implements OnInit {
  accountNumber!: number;
  accountStatementResponse: AccountStatementResponse = new AccountStatementResponse();

  constructor(private transactionService: TransactionServiceService) {}

  ngOnInit(): void {}

  searchAccountStatement(accountNumber: number): void {
    this.transactionService.getAccountStatement(accountNumber).subscribe(data => {
        this.accountStatementResponse = data;
      }
    )
  }
}

