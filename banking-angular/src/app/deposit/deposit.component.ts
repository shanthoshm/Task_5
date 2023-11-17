import { Component } from '@angular/core';
import { DepositRequest } from '../deposit-request';
import { TransactionServiceService } from '../transaction-service.service';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})

export class DepositComponent {
  depositRequest: DepositRequest = new DepositRequest();
  accountNumber = this.depositRequest.accountNumber;
  depositAmount = this.depositRequest.depositAmount;
  
  constructor(private transactionservice: TransactionServiceService){
  }

  deposit(depositRequest: DepositRequest){
    this.transactionservice.deposit(this.depositRequest).subscribe((data: any) => 
      console.log(data), (error: any) =>
      console.log(error));
      this.depositRequest = new DepositRequest;
  }
}
