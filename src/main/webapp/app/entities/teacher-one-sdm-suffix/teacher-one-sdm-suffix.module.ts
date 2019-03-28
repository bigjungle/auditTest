import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TestauditSharedModule } from 'app/shared';
import {
    TeacherOneSdmSuffixComponent,
    TeacherOneSdmSuffixDetailComponent,
    TeacherOneSdmSuffixUpdateComponent,
    TeacherOneSdmSuffixDeletePopupComponent,
    TeacherOneSdmSuffixDeleteDialogComponent,
    teacherOneRoute,
    teacherOnePopupRoute
} from './';

const ENTITY_STATES = [...teacherOneRoute, ...teacherOnePopupRoute];

@NgModule({
    imports: [TestauditSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TeacherOneSdmSuffixComponent,
        TeacherOneSdmSuffixDetailComponent,
        TeacherOneSdmSuffixUpdateComponent,
        TeacherOneSdmSuffixDeleteDialogComponent,
        TeacherOneSdmSuffixDeletePopupComponent
    ],
    entryComponents: [
        TeacherOneSdmSuffixComponent,
        TeacherOneSdmSuffixUpdateComponent,
        TeacherOneSdmSuffixDeleteDialogComponent,
        TeacherOneSdmSuffixDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestauditTeacherOneSdmSuffixModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
