import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Course1802753139SharedModule } from 'app/shared/shared.module';
import { UserContentCommentComponent } from './user-content-comment.component';
import { UserContentCommentDetailComponent } from './user-content-comment-detail.component';
import { UserContentCommentUpdateComponent } from './user-content-comment-update.component';
import { UserContentCommentDeleteDialogComponent } from './user-content-comment-delete-dialog.component';
import { userContentCommentRoute } from './user-content-comment.route';

@NgModule({
  imports: [Course1802753139SharedModule, RouterModule.forChild(userContentCommentRoute)],
  declarations: [
    UserContentCommentComponent,
    UserContentCommentDetailComponent,
    UserContentCommentUpdateComponent,
    UserContentCommentDeleteDialogComponent,
  ],
  entryComponents: [UserContentCommentDeleteDialogComponent],
})
export class Course1802753139UserContentCommentModule {}
