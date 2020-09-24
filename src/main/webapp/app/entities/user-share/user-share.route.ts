import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserShare, UserShare } from 'app/shared/model/user-share.model';
import { UserShareService } from './user-share.service';
import { UserShareComponent } from './user-share.component';
import { UserShareDetailComponent } from './user-share-detail.component';
import { UserShareUpdateComponent } from './user-share-update.component';

@Injectable({ providedIn: 'root' })
export class UserShareResolve implements Resolve<IUserShare> {
  constructor(private service: UserShareService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserShare> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userShare: HttpResponse<UserShare>) => {
          if (userShare.body) {
            return of(userShare.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserShare());
  }
}

export const userShareRoute: Routes = [
  {
    path: '',
    component: UserShareComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'course1802753139App.userShare.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserShareDetailComponent,
    resolve: {
      userShare: UserShareResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userShare.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserShareUpdateComponent,
    resolve: {
      userShare: UserShareResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userShare.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserShareUpdateComponent,
    resolve: {
      userShare: UserShareResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userShare.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
