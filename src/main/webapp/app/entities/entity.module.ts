import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'student-one-sdm-suffix',
                loadChildren: './student-one-sdm-suffix/student-one-sdm-suffix.module#TestauditStudentOneSdmSuffixModule'
            },
            {
                path: 'teacher-one-sdm-suffix',
                loadChildren: './teacher-one-sdm-suffix/teacher-one-sdm-suffix.module#TestauditTeacherOneSdmSuffixModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestauditEntityModule {}
