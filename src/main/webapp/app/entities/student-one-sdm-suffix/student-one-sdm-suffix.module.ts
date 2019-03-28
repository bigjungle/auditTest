import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TestauditSharedModule } from 'app/shared';
import {
    StudentOneSdmSuffixComponent,
    StudentOneSdmSuffixDetailComponent,
    StudentOneSdmSuffixUpdateComponent,
    StudentOneSdmSuffixDeletePopupComponent,
    StudentOneSdmSuffixDeleteDialogComponent,
    studentOneRoute,
    studentOnePopupRoute
} from './';

const ENTITY_STATES = [...studentOneRoute, ...studentOnePopupRoute];

@NgModule({
    imports: [TestauditSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentOneSdmSuffixComponent,
        StudentOneSdmSuffixDetailComponent,
        StudentOneSdmSuffixUpdateComponent,
        StudentOneSdmSuffixDeleteDialogComponent,
        StudentOneSdmSuffixDeletePopupComponent
    ],
    entryComponents: [
        StudentOneSdmSuffixComponent,
        StudentOneSdmSuffixUpdateComponent,
        StudentOneSdmSuffixDeleteDialogComponent,
        StudentOneSdmSuffixDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestauditStudentOneSdmSuffixModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
