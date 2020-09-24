import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Course1802753139SharedModule } from 'app/shared/shared.module';
import { UserContentPraiseComponent } from './user-content-praise.component';
import { UserContentPraiseDetailComponent } from './user-content-praise-detail.component';
import { UserContentPraiseUpdateComponent } from './user-content-praise-update.component';
import { UserContentPraiseDeleteDialogComponent } from './user-content-praise-delete-dialog.component';
import { userContentPraiseRoute } from './user-content-praise.route';

@NgModule({
  imports: [Course1802753139SharedModule, RouterModule.forChild(userContentPraiseRoute)],
  declarations: [
    UserContentPraiseComponent,
    UserContentPraiseDetailComponent,
    UserContentPraiseUpdateComponent,
    UserContentPraiseDeleteDialogComponent,
  ],
  entryComponents: [UserContentPraiseDeleteDialogComponent],
})
export class Course1802753139UserContentPraiseModule {}
