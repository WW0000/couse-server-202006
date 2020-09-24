import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Course1802753139SharedModule } from 'app/shared/shared.module';
import { UserAccountComponent } from './user-account.component';
import { UserAccountDetailComponent } from './user-account-detail.component';
import { UserAccountUpdateComponent } from './user-account-update.component';
import { UserAccountDeleteDialogComponent } from './user-account-delete-dialog.component';
import { userAccountRoute } from './user-account.route';

@NgModule({
  imports: [Course1802753139SharedModule, RouterModule.forChild(userAccountRoute)],
  declarations: [UserAccountComponent, UserAccountDetailComponent, UserAccountUpdateComponent, UserAccountDeleteDialogComponent],
  entryComponents: [UserAccountDeleteDialogComponent],
})
export class Course1802753139UserAccountModule {}
