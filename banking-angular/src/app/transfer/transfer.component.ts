import { Component } from '@angular/core';
import { TransferRequest } from '../transfer-request';
import { TransactionServiceService } from '../transaction-service.service';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent {
  transferRequest: TransferRequest = new TransferRequest();
  fromAccountNumber = this.transferRequest.fromAccountNumber;
  toAccountNumber = this.transferRequest.toAccountNumber;
  transferAmount = this.transferRequest.transferAmount;
  
  constructor(private transactionservice: TransactionServiceService){
  }

  transfer(transferRequest: TransferRequest){
    this.transactionservice.transfer(this.transferRequest).subscribe((data: any) => 
      console.log(data), (error: any) =>
      console.log(error));
      this.transferRequest = new TransferRequest;
  }
}
