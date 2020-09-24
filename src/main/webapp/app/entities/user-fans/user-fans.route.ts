import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserFans, UserFans } from 'app/shared/model/user-fans.model';
import { UserFansService } from './user-fans.service';
import { UserFansComponent } from './user-fans.component';
import { UserFansDetailComponent } from './user-fans-detail.component';
import { UserFansUpdateComponent } from './user-fans-update.component';

@Injectable({ providedIn: 'root' })
export class UserFansResolve implements Resolve<IUserFans> {
  constructor(private service: UserFansService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserFans> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userFans: HttpResponse<UserFans>) => {
          if (userFans.body) {
            return of(userFans.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserFans());
  }
}

export const userFansRoute: Routes = [
  {
    path: '',
    component: UserFansComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'course1802753139App.userFans.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserFansDetailComponent,
    resolve: {
      userFans: UserFansResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userFans.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserFansUpdateComponent,
    resolve: {
      userFans: UserFansResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userFans.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserFansUpdateComponent,
    resolve: {
      userFans: UserFansResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userFans.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
