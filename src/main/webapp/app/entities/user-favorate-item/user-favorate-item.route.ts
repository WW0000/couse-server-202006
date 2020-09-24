import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserFavorateItem, UserFavorateItem } from 'app/shared/model/user-favorate-item.model';
import { UserFavorateItemService } from './user-favorate-item.service';
import { UserFavorateItemComponent } from './user-favorate-item.component';
import { UserFavorateItemDetailComponent } from './user-favorate-item-detail.component';
import { UserFavorateItemUpdateComponent } from './user-favorate-item-update.component';

@Injectable({ providedIn: 'root' })
export class UserFavorateItemResolve implements Resolve<IUserFavorateItem> {
  constructor(private service: UserFavorateItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserFavorateItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userFavorateItem: HttpResponse<UserFavorateItem>) => {
          if (userFavorateItem.body) {
            return of(userFavorateItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserFavorateItem());
  }
}

export const userFavorateItemRoute: Routes = [
  {
    path: '',
    component: UserFavorateItemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'course1802753139App.userFavorateItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserFavorateItemDetailComponent,
    resolve: {
      userFavorateItem: UserFavorateItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userFavorateItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserFavorateItemUpdateComponent,
    resolve: {
      userFavorateItem: UserFavorateItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userFavorateItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserFavorateItemUpdateComponent,
    resolve: {
      userFavorateItem: UserFavorateItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.userFavorateItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
