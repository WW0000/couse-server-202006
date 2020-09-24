import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Course1802753139SharedModule } from 'app/shared/shared.module';
import { UserShareComponent } from './user-share.component';
import { UserShareDetailComponent } from './user-share-detail.component';
import { UserShareUpdateComponent } from './user-share-update.component';
import { UserShareDeleteDialogComponent } from './user-share-delete-dialog.component';
import { userShareRoute } from './user-share.route';

@NgModule({
  imports: [Course1802753139SharedModule, RouterModule.forChild(userShareRoute)],
  declarations: [UserShareComponent, UserShareDetailComponent, UserShareUpdateComponent, UserShareDeleteDialogComponent],
  entryComponents: [UserShareDeleteDialogComponent],
})
export class Course1802753139UserShareModule {}
