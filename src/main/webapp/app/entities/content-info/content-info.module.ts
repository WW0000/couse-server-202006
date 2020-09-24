import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Course1802753139SharedModule } from 'app/shared/shared.module';
import { ContentInfoComponent } from './content-info.component';
import { ContentInfoDetailComponent } from './content-info-detail.component';
import { ContentInfoUpdateComponent } from './content-info-update.component';
import { ContentInfoDeleteDialogComponent } from './content-info-delete-dialog.component';
import { contentInfoRoute } from './content-info.route';

@NgModule({
  imports: [Course1802753139SharedModule, RouterModule.forChild(contentInfoRoute)],
  declarations: [ContentInfoComponent, ContentInfoDetailComponent, ContentInfoUpdateComponent, ContentInfoDeleteDialogComponent],
  entryComponents: [ContentInfoDeleteDialogComponent],
})
export class Course1802753139ContentInfoModule {}
