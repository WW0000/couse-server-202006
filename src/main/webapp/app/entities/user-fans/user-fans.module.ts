import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Course1802753139SharedModule } from 'app/shared/shared.module';
import { UserFansComponent } from './user-fans.component';
import { UserFansDetailComponent } from './user-fans-detail.component';
import { UserFansUpdateComponent } from './user-fans-update.component';
import { UserFansDeleteDialogComponent } from './user-fans-delete-dialog.component';
import { userFansRoute } from './user-fans.route';

@NgModule({
  imports: [Course1802753139SharedModule, RouterModule.forChild(userFansRoute)],
  declarations: [UserFansComponent, UserFansDetailComponent, UserFansUpdateComponent, UserFansDeleteDialogComponent],
  entryComponents: [UserFansDeleteDialogComponent],
})
export class Course1802753139UserFansModule {}
