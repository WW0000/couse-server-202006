import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Course1802753139SharedModule } from 'app/shared/shared.module';
import { UserFavorateItemComponent } from './user-favorate-item.component';
import { UserFavorateItemDetailComponent } from './user-favorate-item-detail.component';
import { UserFavorateItemUpdateComponent } from './user-favorate-item-update.component';
import { UserFavorateItemDeleteDialogComponent } from './user-favorate-item-delete-dialog.component';
import { userFavorateItemRoute } from './user-favorate-item.route';

@NgModule({
  imports: [Course1802753139SharedModule, RouterModule.forChild(userFavorateItemRoute)],
  declarations: [
    UserFavorateItemComponent,
    UserFavorateItemDetailComponent,
    UserFavorateItemUpdateComponent,
    UserFavorateItemDeleteDialogComponent,
  ],
  entryComponents: [UserFavorateItemDeleteDialogComponent],
})
export class Course1802753139UserFavorateItemModule {}
