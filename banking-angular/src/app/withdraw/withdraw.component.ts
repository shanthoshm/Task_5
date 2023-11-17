import { Component } from '@angular/core';
import { WithdrawRequest } from '../withdraw-request';
import { TransactionServiceService } from '../transaction-service.service';

@Component({
  selector: 'app-withdraw',
  templateUrl: './withdraw.component.html',
  styleUrls: ['./withdraw.component.css']
})
export class WithdrawComponent {
  withdrawRequest: WithdrawRequest = new WithdrawRequest();
  accountNumber = this.withdrawRequest.accountNumber;
  withdrawalAmount = this.withdrawRequest.withdrawalAmount;
  
  constructor(private transactionservice: TransactionServiceService){
  }

  withdraw(withdrawRequest: WithdrawRequest){
    this.transactionservice.withdraw(this.withdrawRequest).subscribe((data: any) => 
      console.log(data), (error: any) =>
      console.log(error));
      this.withdrawRequest = new WithdrawRequest;
  }
}