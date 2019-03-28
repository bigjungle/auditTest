import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TeacherOneSdmSuffix } from 'app/shared/model/teacher-one-sdm-suffix.model';
import { TeacherOneSdmSuffixService } from './teacher-one-sdm-suffix.service';
import { TeacherOneSdmSuffixComponent } from './teacher-one-sdm-suffix.component';
import { TeacherOneSdmSuffixDetailComponent } from './teacher-one-sdm-suffix-detail.component';
import { TeacherOneSdmSuffixUpdateComponent } from './teacher-one-sdm-suffix-update.component';
import { TeacherOneSdmSuffixDeletePopupComponent } from './teacher-one-sdm-suffix-delete-dialog.component';
import { ITeacherOneSdmSuffix } from 'app/shared/model/teacher-one-sdm-suffix.model';

@Injectable({ providedIn: 'root' })
export class TeacherOneSdmSuffixResolve implements Resolve<ITeacherOneSdmSuffix> {
    constructor(private service: TeacherOneSdmSuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITeacherOneSdmSuffix> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TeacherOneSdmSuffix>) => response.ok),
                map((teacherOne: HttpResponse<TeacherOneSdmSuffix>) => teacherOne.body)
            );
        }
        return of(new TeacherOneSdmSuffix());
    }
}

export const teacherOneRoute: Routes = [
    {
        path: '',
        component: TeacherOneSdmSuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testauditApp.teacherOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TeacherOneSdmSuffixDetailComponent,
        resolve: {
            teacherOne: TeacherOneSdmSuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testauditApp.teacherOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TeacherOneSdmSuffixUpdateComponent,
        resolve: {
            teacherOne: TeacherOneSdmSuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testauditApp.teacherOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TeacherOneSdmSuffixUpdateComponent,
        resolve: {
            teacherOne: TeacherOneSdmSuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testauditApp.teacherOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teacherOnePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TeacherOneSdmSuffixDeletePopupComponent,
        resolve: {
            teacherOne: TeacherOneSdmSuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testauditApp.teacherOne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
