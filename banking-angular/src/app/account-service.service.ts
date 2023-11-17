import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Account } from './account';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})

export class AccountServiceService {
  url = 'http://localhost:8071/account';

  httpHeader = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private httpClient: HttpClient, private router: Router) {}

  addAccount(account: Account): Observable<Account>{
    return this.httpClient.post<any>(`${this.url}/add`, account);
  }

  deleteAccount(accountNumber: number): Observable<any> {
    return this.httpClient.delete(`${this.url}/${accountNumber}`);
  }

  getAccount(accountNumber: any): Observable<any> {
    return this.httpClient.get(`${this.url}/${accountNumber}`, this.httpHeader);
  }

  changeAccountStatus(accountNumber: number, accountStatus: string): Observable<string> {
    return this.httpClient.put<string>(`${this.url}/${accountNumber}/${accountStatus}`, null);
  }
}
