import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContentInfo, ContentInfo } from 'app/shared/model/content-info.model';
import { ContentInfoService } from './content-info.service';
import { ContentInfoComponent } from './content-info.component';
import { ContentInfoDetailComponent } from './content-info-detail.component';
import { ContentInfoUpdateComponent } from './content-info-update.component';

@Injectable({ providedIn: 'root' })
export class ContentInfoResolve implements Resolve<IContentInfo> {
  constructor(private service: ContentInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContentInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contentInfo: HttpResponse<ContentInfo>) => {
          if (contentInfo.body) {
            return of(contentInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContentInfo());
  }
}

export const contentInfoRoute: Routes = [
  {
    path: '',
    component: ContentInfoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'course1802753139App.contentInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContentInfoDetailComponent,
    resolve: {
      contentInfo: ContentInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.contentInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContentInfoUpdateComponent,
    resolve: {
      contentInfo: ContentInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.contentInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContentInfoUpdateComponent,
    resolve: {
      contentInfo: ContentInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'course1802753139App.contentInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
