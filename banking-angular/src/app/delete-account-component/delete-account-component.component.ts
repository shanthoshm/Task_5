import { Component } from '@angular/core';
import { Account } from '../account';
import { AccountServiceService } from '../account-service.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-delete-account-component',
  templateUrl: './delete-account-component.component.html',
  styleUrls: ['./delete-account-component.component.css']
})
export class DeleteAccountComponentComponent {
  accountNumber!: number;
  account: Account = new Account();

  constructor(private accountService: AccountServiceService, private router: Router, private route: ActivatedRoute){

  }

  ngOnIt(): void{
    this.accountNumber = this.route.snapshot.params['accountNumber'];
    this.deleteAccount(this.accountNumber);
  }

  deleteAccount(accountNumber: number){
    this.accountService.deleteAccount(this.account.accountNumber).subscribe(
      () => {
        this.router.navigate([]);
      }
      
    );
  }
}