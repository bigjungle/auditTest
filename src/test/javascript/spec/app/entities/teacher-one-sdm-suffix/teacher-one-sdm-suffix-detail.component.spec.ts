/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestauditTestModule } from '../../../test.module';
import { TeacherOneSdmSuffixDetailComponent } from 'app/entities/teacher-one-sdm-suffix/teacher-one-sdm-suffix-detail.component';
import { TeacherOneSdmSuffix } from 'app/shared/model/teacher-one-sdm-suffix.model';

describe('Component Tests', () => {
    describe('TeacherOneSdmSuffix Management Detail Component', () => {
        let comp: TeacherOneSdmSuffixDetailComponent;
        let fixture: ComponentFixture<TeacherOneSdmSuffixDetailComponent>;
        const route = ({ data: of({ teacherOne: new TeacherOneSdmSuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestauditTestModule],
                declarations: [TeacherOneSdmSuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TeacherOneSdmSuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TeacherOneSdmSuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.teacherOne).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
