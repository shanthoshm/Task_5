import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateAccountComponentComponent } from './create-account-component/create-account-component.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { DeleteAccountComponentComponent } from './delete-account-component/delete-account-component.component';
import { SearchAccountComponentComponent } from './search-account-component/search-account-component.component';
import { ChangeAccountStatusComponent } from './change-account-status/change-account-status.component';
import { DepositComponent } from './deposit/deposit.component';
import { WithdrawComponent } from './withdraw/withdraw.component';
import { TransferComponent } from './transfer/transfer.component';
import { SearchAccountStatementComponent } from './search-account-statement/search-account-statement.component';

const routes: Routes = [
  { path: "app-nav-bar", component: NavBarComponent},
  { path: "change-account-status", component: ChangeAccountStatusComponent},
  { path: "delete-account", component: DeleteAccountComponentComponent},
  { path: "create-account", component: CreateAccountComponentComponent },
  { path: "search-account", component: SearchAccountComponentComponent},
  { path: "deposit", component: DepositComponent},
  { path: "withdraw", component: WithdrawComponent},
  { path: "transfer", component: TransferComponent},
  { path: "statement", component: SearchAccountStatementComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
