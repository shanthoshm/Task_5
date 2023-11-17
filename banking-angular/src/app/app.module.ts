import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateAccountComponentComponent } from './create-account-component/create-account-component.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { DeleteAccountComponentComponent } from './delete-account-component/delete-account-component.component';
import { SearchAccountComponentComponent } from './search-account-component/search-account-component.component';
import { ChangeAccountStatusComponent } from './change-account-status/change-account-status.component';
import { DepositComponent } from './deposit/deposit.component';
import { WithdrawComponent } from './withdraw/withdraw.component';
import { TransferComponent } from './transfer/transfer.component';
import { SearchAccountStatementComponent } from './search-account-statement/search-account-statement.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    CreateAccountComponentComponent,
    DeleteAccountComponentComponent,
    SearchAccountComponentComponent,
    ChangeAccountStatusComponent,
    DepositComponent,
    WithdrawComponent,
    TransferComponent,
    SearchAccountStatementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
