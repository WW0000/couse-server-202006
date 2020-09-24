import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserContentPraise, UserContentPraise } from 'app/shared/model/user-content-praise.model';
import { UserContentPraiseService } from './user-content-praise.service';
import { UserContentPraiseComponent } from './user-content-praise.component';
import { UserContentPraiseDetailComponent } from './user-content-praise-detail.component';
import { UserContentPraiseUpdateComponent } from './user-content-praise-update.component';

@Injectable({ providedIn: 'root' })
export class UserContentPraiseResolve implements Resolve<IUserContentPraise> {
  constructor(private service: UserContentPraiseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserContentPraise> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userContentPraise: HttpResponse<UserContentPraise>) => {
          if (userContentPraise.body) {
            return of(userContentPraise.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserContentPraise());
  }
}

export const userContentPraiseRoute: Routes = [
  {
    path: '',
    component: UserContentPraiseComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'course1802753139App.userContentPraise.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserContentPraiseDetailComponent,
    resolve: {
      userContentPraise: UserContentPraiseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userContentPraise.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserContentPraiseUpdateComponent,
    resolve: {
      userContentPraise: UserContentPraiseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userContentPraise.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserContentPraiseUpdateComponent,
    resolve: {
      userContentPraise: UserContentPraiseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userContentPraise.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
