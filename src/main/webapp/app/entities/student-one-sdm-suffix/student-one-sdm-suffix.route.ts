import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentOneSdmSuffix } from 'app/shared/model/student-one-sdm-suffix.model';
import { StudentOneSdmSuffixService } from './student-one-sdm-suffix.service';
import { StudentOneSdmSuffixComponent } from './student-one-sdm-suffix.component';
import { StudentOneSdmSuffixDetailComponent } from './student-one-sdm-suffix-detail.component';
import { StudentOneSdmSuffixUpdateComponent } from './student-one-sdm-suffix-update.component';
import { StudentOneSdmSuffixDeletePopupComponent } from './student-one-sdm-suffix-delete-dialog.component';
import { IStudentOneSdmSuffix } from 'app/shared/model/student-one-sdm-suffix.model';

@Injectable({ providedIn: 'root' })
export class StudentOneSdmSuffixResolve implements Resolve<IStudentOneSdmSuffix> {
    constructor(private service: StudentOneSdmSuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentOneSdmSuffix> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentOneSdmSuffix>) => response.ok),
                map((studentOne: HttpResponse<StudentOneSdmSuffix>) => studentOne.body)
            );
        }
        return of(new StudentOneSdmSuffix());
    }
}

export const studentOneRoute: Routes = [
    {
        path: '',
        component: StudentOneSdmSuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testauditApp.studentOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StudentOneSdmSuffixDetailComponent,
        resolve: {
            studentOne: StudentOneSdmSuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testauditApp.studentOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StudentOneSdmSuffixUpdateComponent,
        resolve: {
            studentOne: StudentOneSdmSuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testauditApp.studentOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StudentOneSdmSuffixUpdateComponent,
        resolve: {
            studentOne: StudentOneSdmSuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testauditApp.studentOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentOnePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StudentOneSdmSuffixDeletePopupComponent,
        resolve: {
            studentOne: StudentOneSdmSuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testauditApp.studentOne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
