import { Component } from '@angular/core';
import { AccountServiceService } from '../account-service.service';

@Component({
  selector: 'app-change-account-status',
  templateUrl: './change-account-status.component.html',
  styleUrls: ['./change-account-status.component.css']
})
export class ChangeAccountStatusComponent {
  accountNumber!: number;
  accountStatus!: string;
  message!: string;

  constructor(private accountService: AccountServiceService) {}

  onSubmit() {
    this.accountService.changeAccountStatus(this.accountNumber, this.accountStatus)
      .subscribe(() => {
        console.log("Status changed successfully")
    });
  }
}