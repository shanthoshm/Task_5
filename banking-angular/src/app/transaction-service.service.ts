import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { DepositRequest } from './deposit-request';
import { Observable, catchError, map, throwError } from 'rxjs';
import { WithdrawRequest } from './withdraw-request';
import { TransferRequest } from './transfer-request';
import { AccountStatementResponse } from './account-statement-response';


@Injectable({
  providedIn: 'root'
})

export class TransactionServiceService {
  url = 'http://localhost:8072/transaction';

  httpHeader = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private httpClient: HttpClient, private router: Router) {}

  deposit(depositRequest:DepositRequest): Observable<any>{
    return this.httpClient.put<any>(`${this.url}/deposit`, depositRequest);
  }

  withdraw(withdrawRequest:WithdrawRequest): Observable<any>{
    return this.httpClient.put<any>(`${this.url}/withdraw`, withdrawRequest);
  }

  transfer(transferRequest:TransferRequest): Observable<any>{
    return this.httpClient.put<any>(`${this.url}/transfer`, transferRequest);
  }

  getAccountStatement(accountNumber: number): Observable<AccountStatementResponse> {
    return this.httpClient.get<AccountStatementResponse>(`${this.url}/statement/${accountNumber}`);
  }
}
