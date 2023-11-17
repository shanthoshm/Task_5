import { Component } from '@angular/core';
import { Account } from '../account';
import { ActivatedRoute } from '@angular/router';
import { AccountServiceService } from '../account-service.service';

@Component({
  selector: 'app-search-account-component',
  templateUrl: './search-account-component.component.html',
  styleUrls: ['./search-account-component.component.css']
})
export class SearchAccountComponentComponent {
  accountNumber! : number;
  account: Account = new Account();

  constructor(private route: ActivatedRoute, private accountService: AccountServiceService) { }

  ngOnInit(): void {
    this.accountNumber = this.route.snapshot.params['accountNumber'];
    this.getAccount(this.accountNumber);
  }

  getAccount(accountNumber: number): void {
    this.accountService.getAccount(accountNumber).subscribe(data => {
      this.account = data;
    });
  }
}
