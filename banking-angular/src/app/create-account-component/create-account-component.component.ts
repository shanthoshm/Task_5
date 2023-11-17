import { Component } from '@angular/core';
import { Account } from '../account';
import { Router } from '@angular/router';
import { AccountServiceService } from '../account-service.service';

@Component({
  selector: 'app-create-account-component',
  templateUrl: './create-account-component.component.html',
  styleUrls: ['./create-account-component.component.css']
})

export class CreateAccountComponentComponent {
  accountNumber!: number;
  account: Account = new Account();
  

  constructor(private accountService: AccountServiceService, private router: Router){}

  addAccount() {
    this.accountService.addAccount(this.account).subscribe(data => {
      console.log(data);
      this.account = data;
      this.accountNumber = this.account.accountNumber;
      //this.router.navigate(['']);
    });
  }

  getAccount(accountNumber: number): void {
    this.accountService.getAccount(accountNumber).subscribe(data => {
      this.account = data;
    });
  }

}