import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserContentComment, UserContentComment } from 'app/shared/model/user-content-comment.model';
import { UserContentCommentService } from './user-content-comment.service';
import { UserContentCommentComponent } from './user-content-comment.component';
import { UserContentCommentDetailComponent } from './user-content-comment-detail.component';
import { UserContentCommentUpdateComponent } from './user-content-comment-update.component';

@Injectable({ providedIn: 'root' })
export class UserContentCommentResolve implements Resolve<IUserContentComment> {
  constructor(private service: UserContentCommentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserContentComment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userContentComment: HttpResponse<UserContentComment>) => {
          if (userContentComment.body) {
            return of(userContentComment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserContentComment());
  }
}

export const userContentCommentRoute: Routes = [
  {
    path: '',
    component: UserContentCommentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'course1802753139App.userContentComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserContentCommentDetailComponent,
    resolve: {
      userContentComment: UserContentCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userContentComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserContentCommentUpdateComponent,
    resolve: {
      userContentComment: UserContentCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userContentComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserContentCommentUpdateComponent,
    resolve: {
      userContentComment: UserContentCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userContentComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
