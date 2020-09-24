import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-account',
        loadChildren: () => import('./user-account/user-account.module').then(m => m.Course1802753139UserAccountModule),
      },
      {
        path: 'user-favorate-item',
        loadChildren: () => import('./user-favorate-item/user-favorate-item.module').then(m => m.Course1802753139UserFavorateItemModule),
      },
      {
        path: 'user-content-comment',
        loadChildren: () =>
          import('./user-content-comment/user-content-comment.module').then(m => m.Course1802753139UserContentCommentModule),
      },
      {
        path: 'user-fans',
        loadChildren: () => import('./user-fans/user-fans.module').then(m => m.Course1802753139UserFansModule),
      },
      {
        path: 'user-content-praise',
        loadChildren: () => import('./user-content-praise/user-content-praise.module').then(m => m.Course1802753139UserContentPraiseModule),
      },
      {
        path: 'user-share',
        loadChildren: () => import('./user-share/user-share.module').then(m => m.Course1802753139UserShareModule),
      },
      {
        path: 'content-info',
        loadChildren: () => import('./content-info/content-info.module').then(m => m.Course1802753139ContentInfoModule),
      },
      {
        path: 'content-type',
        loadChildren: () => import('./content-type/content-type.module').then(m => m.Course1802753139ContentTypeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class Course1802753139EntityModule {}
